package janggi.dao;

import janggi.dto.PieceDto;
import janggi.dto.PieceTypeDto;
import janggi.dto.TeamTypeDto;
import janggi.manager.ConnectionManager;
import janggi.piece.Piece;
import janggi.piece.PieceType;
import janggi.position.Position;
import janggi.team.TeamType;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JanggiDao {

    private final ConnectionManager connectionManager;

    public JanggiDao(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void insertInitialPieceType() {
        final String query = "INSERT INTO piece_type(name) VALUES(?)";

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            for (PieceType pieceType : PieceType.values()) {
                preparedStatement.setString(1, pieceType.getTitle());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 데이터 삽입에 실패하였습니다.");
        }
    }

    public void insertInitialTeam(TeamType currentTeam) {
        final String query = "INSERT INTO team(name, current) VALUES(?, ?)";

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            for (TeamType teamType : TeamType.values()) {
                preparedStatement.setString(1, teamType.getTitle());
                preparedStatement.setBoolean(2, teamType == currentTeam);
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 데이터 삽입에 실패하였습니다.");
        }
    }

    public void insertPieces(Map<Position, Piece> pieces) {
        final String query = """
                    INSERT INTO piece (team_id, piece_type_id, x, y)
                    VALUES (
                        (SELECT id FROM team WHERE name = ?),
                        (SELECT id FROM piece_type WHERE name = ?),
                        ?, ?
                    )
                """;

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();
                preparedStatement.setString(1, piece.getTeamType().getTitle());
                preparedStatement.setString(2, piece.getPieceType().getTitle());
                preparedStatement.setInt(3, position.getX());
                preparedStatement.setInt(4, position.getY());
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();

        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 데이터 삽입에 실패하였습니다.");
        }
    }

    public TeamTypeDto findTeamById(int id) {
        final String query = "SELECT * FROM team WHERE id = ?";

        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new TeamTypeDto(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getBoolean("current")
                );
            }
            throw new SQLException("[ERROR] 데이터 조회에 실패하였습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public PieceTypeDto findPieceTypeById(int id) {
        final String query = "SELECT * FROM piece_type WHERE id = ?";

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            final var resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new RuntimeException("[ERROR] 데이터 조회에 실패하였습니다.");
            }
            return new PieceTypeDto(
                    resultSet.getInt("id"),
                    resultSet.getString("name")
            );
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 데이터 조회에 실패하였습니다.");
        }
    }

    public List<PieceDto> findPieces() {
        final String query = "SELECT * FROM piece";

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            List<PieceDto> pieces = new ArrayList<>();

            while (resultSet.next()) {
                pieces.add(new PieceDto(
                        resultSet.getInt("id"),
                        resultSet.getInt("team_id"),
                        resultSet.getInt("piece_type_id"),
                        resultSet.getInt("x"),
                        resultSet.getInt("y")
                ));
            }

            return pieces;
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 데이터 조회에 실패하였습니다.");
        }
    }

    public List<TeamTypeDto> findTeams() {
        final String query = "SELECT * FROM team ORDER BY current desc";

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final var resultSet = preparedStatement.executeQuery();
            List<TeamTypeDto> teamTypes = new ArrayList<>();

            while (resultSet.next()) {
                teamTypes.add(new TeamTypeDto(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getBoolean("current")
                ));
            }

            return teamTypes;
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 데이터 조회에 실패하였습니다.");
        }
    }

    public void updateTeamOrder(TeamType currentTeam) {
        final String query = """
                    UPDATE Team SET current = CASE 
                        WHEN name = ? THEN 1 
                        ELSE 0 
                    END
                """;

        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, currentTeam.getTitle());
            preparedStatement.executeUpdate();

        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] 팀 순서 변경에 실패하였습니다");
        }
    }

    public void deleteAllPieceIfExists() {
        deleteAllByTableNameIfExists("piece");
    }

    public void deleteAllPieceTypeIfExists() {
        deleteAllByTableNameIfExists("piece_type");
    }

    public void deleteAllTeamIfExists() {
        deleteAllByTableNameIfExists("team");
    }

    private void deleteAllByTableNameIfExists(String table) {
        final String query = "DELETE FROM " + table;
        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("[ERROR] " + table + " 데이터 삭제에 실패하였습니다.");
        }
    }
}
