package janggi.dao.piece;

import janggi.direction.PieceType;
import janggi.dto.PieceDto;
import janggi.dto.PieceMove;
import janggi.piece.players.Team;
import janggi.position.Position;
import janggi.utils.DBUtil;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PieceDaoImpl implements PieceDao {

    private static final DBUtil dbUtil = DBUtil.getInstance();
    private static final PieceDao pieceDao = new PieceDaoImpl();

    private PieceDaoImpl() {
    }

    public static PieceDao getPieceDao() {
        return pieceDao;
    }

    @Override
    public List<PieceDto> select(final Team givenTeam) {
        final var query = "SELECT * FROM piece WHERE team = ?";
        try (final var connection = dbUtil.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            final int givenTeamId = getTeamIdByName(connection, givenTeam.name());
            preparedStatement.setInt(1, givenTeamId);

            final var resultSet = preparedStatement.executeQuery();
            final List<PieceDto> dtos = new ArrayList<>();
            while (resultSet.next()) {
                final Team team = Team.from(getTeamNameById(connection, resultSet.getInt("team")));
                dtos.add(new PieceDto(
                        team,
                        PieceType.from(getPieceTypeById(connection, resultSet.getInt("piecetype")), team),
                        resultSet.getInt("y"),
                        resultSet.getInt("x")
                ));
            }
            return dtos;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PieceDto> selectAll() {
        final var query = "SELECT * FROM piece";
        try (final var connection = dbUtil.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {

            final var resultSet = preparedStatement.executeQuery();
            final List<PieceDto> dtos = new ArrayList<>();
            while (resultSet.next()) {
                final Team team = Team.from(getTeamNameById(connection, resultSet.getInt("team")));
                dtos.add(new PieceDto(
                        team,
                        PieceType.from(getPieceTypeById(connection, resultSet.getInt("piecetype")), team),
                        resultSet.getInt("y"),
                        resultSet.getInt("x")
                ));
            }
            return dtos;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void insert(final PieceDto pieceDto) {
        final var query = "INSERT INTO piece (y, x, team, piecetype) VALUES(?, ?, ?, ?)";
        try (final var connection = dbUtil.getConnection();
             final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pieceDto.y());
            preparedStatement.setInt(2, pieceDto.x());
            final Team team = pieceDto.team();
            final int teamId = getTeamIdByName(connection, team.name());
            preparedStatement.setInt(3, teamId);
            final int pieceTypeId = getPieceTypeIdByName(connection, pieceDto.pieceType());
            preparedStatement.setInt(4, pieceTypeId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(final PieceMove pieceMove) {
        final var updateQuery = "UPDATE piece SET y = ?, x= ? WHERE y = ? AND x=? AND team=? AND piecetype = ?";
        try (final var connection = dbUtil.getConnection();
             final var preparedStatement = connection.prepareStatement(updateQuery)) {
            final Position currentPosition = pieceMove.currentPosition();
            final Position arrivalPosition = pieceMove.arrivalPosition();
            preparedStatement.setInt(1, arrivalPosition.getY());
            preparedStatement.setInt(2, arrivalPosition.getX());
            preparedStatement.setInt(3, currentPosition.getY());
            preparedStatement.setInt(4, currentPosition.getX());
            final Team team = pieceMove.team();
            final int teamId = getTeamIdByName(connection, team.name());
            preparedStatement.setInt(5, teamId);
            final int pieceTypeId = getPieceTypeIdByName(connection, pieceMove.pieceType());
            preparedStatement.setInt(6, pieceTypeId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(final PieceMove pieceMove) {
        final var deleteQuery = "DELETE FROM piece WHERE y = ? AND x = ? AND team = ? AND piecetype = ?";
        try (final var connection = dbUtil.getConnection();
             final var preparedStatement = connection.prepareStatement(deleteQuery)) {
            final Position arrivalPosition = pieceMove.arrivalPosition();
            preparedStatement.setInt(1, arrivalPosition.getY());
            preparedStatement.setInt(2, arrivalPosition.getX());
            final Team opponentTeam = pieceMove.team().getOppositeTeam();
            preparedStatement.setInt(3, getTeamIdByName(connection, opponentTeam.name()));
            preparedStatement.setInt(4,
                    getPieceTypeIdByName(connection, pieceMove.caughtPieceType()));
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAll() {
        final var deleteQuery = "DELETE FROM piece WHERE piece_id > 0";
        try (final var connection = dbUtil.getConnection();
             final var preparedStatement = connection.prepareStatement(deleteQuery)) {
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTeamNameById(final Connection connection, final int teamId) {
        final var query = "SELECT name FROM team WHERE team_id = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, teamId);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("[ERROR] Team을 찾을 수 없습니다.");
    }

    private int getTeamIdByName(final Connection connection, final String teamName) {
        final var query = "SELECT * FROM team WHERE name = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, teamName);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("team_id");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("[ERROR] Team을 찾을 수 없습니다.");
    }

    private String getPieceTypeById(final Connection connection, final int pieceTypeId) {
        final var query = "SELECT name FROM piecetype WHERE piecetype_id = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, pieceTypeId);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("name");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("[ERROR] PieceType을 찾을 수 없습니다.");
    }

    private int getPieceTypeIdByName(final Connection connection, final PieceType pieceType) {
        final String pieceTypeName = makePieceTypeName(pieceType);

        final var query = "SELECT * FROM piecetype WHERE name = ?";
        try (final var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, pieceTypeName);

            final var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("piecetype_id");
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException("[ERROR] PieceType을 찾을 수 없습니다.");
    }

    private String makePieceTypeName(final PieceType pieceType) {
        if (pieceType.isSoldier()) {
            return pieceType.name().substring(4);
        }
        return pieceType.name();
    }
}
