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
        final String query = "INSERT INTO pieceType(name) VALUES(?)";

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            for (PieceType pieceType : PieceType.values()) {
                preparedStatement.setString(1, pieceType.toString());
                preparedStatement.executeUpdate();
            }
        } catch (final SQLException e) {
            System.out.println("[ERROR] 데이터 삽입에 실패하였습니다.");
        }
    }

    public void insertInitialTeam(List<TeamType> teamTypes, TeamType currentTeam) {
        final String query = "INSERT INTO team(name, current) VALUES(?, ?)";

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            for (TeamType teamType : teamTypes) {
                preparedStatement.setString(1, teamType.getTitle());
                preparedStatement.setBoolean(2, teamType == currentTeam);
                preparedStatement.executeUpdate();
            }
        } catch (final SQLException e) {
            System.out.println("[ERROR] 데이터 삽입에 실패하였습니다.");
        }
    }

    public void insertInitialPieces(Map<Position, Piece> pieces) {
        final String query = "INSERT INTO piece(teamId, pieceTypeId, x, y) VALUES(?, ?, ?, ?)";

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            for (Map.Entry<Position, Piece> entry : pieces.entrySet()) {
                Position position = entry.getKey();
                Piece piece = entry.getValue();
                preparedStatement.setInt(1, findTeamType(piece.getTeamType()).id());
                preparedStatement.setInt(2, findPieceType(piece.getPieceType()).id());
                preparedStatement.setInt(3, position.getX());
                preparedStatement.setInt(4, position.getY());
                preparedStatement.executeUpdate();
            }

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public TeamTypeDto findTeamType(TeamType teamType) {
        final String query = "SELECT * FROM team WHERE name = ?";

        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, teamType.getTitle());

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

    public PieceTypeDto findPieceType(PieceType pieceType) {
        final String query = "SELECT * FROM pieceType WHERE name = ?";

        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pieceType.toString());

            final var resultSet = preparedStatement.executeQuery();
            if (!resultSet.next()) {
                throw new IllegalArgumentException("찾을 수 없음");
            }
            return new PieceTypeDto(
                    resultSet.getInt("id"),
                    resultSet.getString("name")
            );
        } catch (final SQLException e) {
            throw new RuntimeException(e);
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
                        resultSet.getInt("teamId"),
                        resultSet.getInt("pieceTypeId"),
                        resultSet.getInt("x"),
                        resultSet.getInt("y")
                ));
            }

            return pieces;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateTeamOrder() {
        final String query = "UPDATE Team SET current = IF(current = 1, 0, 1);";

        try (final var connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAllPiece() {
        deleteAllByTableName("piece");
    }

    public void deleteAllPieceType() {
        deleteAllByTableName("pieceType");
    }

    public void deleteAllTeam() {
        deleteAllByTableName("team");
    }

    private void deleteAllByTableName(String table) {
        final String query = "DELETE FROM " + table;
        try (final Connection connection = connectionManager.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
