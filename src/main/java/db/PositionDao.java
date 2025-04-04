package db;

import domain.Team;
import domain.piece.PieceType;
import domain.position.Point;
import domain.position.PointValue;
import domain.position.Position;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PositionDao {

    private final DatabaseConnector databaseConnector;

    public PositionDao(DatabaseConnector databaseConnector) {
        this.databaseConnector = databaseConnector;
    }

    public void savePosition(final Position position) {
        Team team = Team.RED;
        if (position.isGreenTeam()) {
            team = Team.GREEN;
        }

        final var insertPointSql = "INSERT INTO point(x, y) VALUES(?, ?)";
        final var insertPieceSql = "INSERT INTO piece(pieceType, team, pointId) VALUES(?, ?, ?)";

        try (final var connection = databaseConnector.getConnection();
             final var pointStmt = connection.prepareStatement(insertPointSql, Statement.RETURN_GENERATED_KEYS);
             final var pieceStmt = connection.prepareStatement(insertPieceSql)) {

            pointStmt.setInt(1, position.getPointValue().x());
            pointStmt.setInt(2, position.getPointValue().y());
            pointStmt.executeUpdate();

            try (final ResultSet generatedKeys = pointStmt.getGeneratedKeys()) {
                if (!generatedKeys.next()) {
                    throw new SQLException("point ID 생성 실패");
                }
                final int pointId = generatedKeys.getInt(1);

                pieceStmt.setString(1, position.getPieceType().name());
                pieceStmt.setString(2, team.name());
                pieceStmt.setInt(3, pointId);
                pieceStmt.executeUpdate();
            }

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePoint(final PointValue fromPoint, final PointValue toPoint) {
        final var updatePointSql = "UPDATE point SET x = ?, y = ? WHERE id = ?";

        try (final var connection = databaseConnector.getConnection();
             final var updatePointStmt = connection.prepareStatement(updatePointSql)) {

            int pointId = findPointIdByCoordinates(connection, fromPoint);
            if (pointId == -1) {
                return;
            }
            updatePointStmt.setInt(1, toPoint.x());
            updatePointStmt.setInt(2, toPoint.y());
            updatePointStmt.setInt(3, pointId);
            updatePointStmt.executeUpdate();

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePosition(final PointValue pointValue) {
        final var deletePieceSql = "DELETE FROM piece WHERE pointId = ?";
        final var deletePointSql = "DELETE FROM point WHERE id = ?";

        try (final var connection = databaseConnector.getConnection();
             final var deletePieceStmt = connection.prepareStatement(deletePieceSql);
             final var deletePointStmt = connection.prepareStatement(deletePointSql)) {

            int pointId = findPointIdByCoordinates(connection, pointValue);
            if (pointId == -1) {
                return;
            }
            deletePieceStmt.setInt(1, pointId);
            deletePieceStmt.executeUpdate();

            deletePointStmt.setInt(1, pointId);
            deletePointStmt.executeUpdate();

        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private int findPointIdByCoordinates(Connection connection, PointValue pointValue) {
        final var findPointSql = "SELECT id FROM point WHERE x = ? AND y = ?";
        try (final var stmt = connection.prepareStatement(findPointSql)) {
            stmt.setInt(1, pointValue.x());
            stmt.setInt(2, pointValue.y());
            try (final ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
                return -1;
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Position> getPositions() {
        final List<Position> positions = new ArrayList<>();
        final var positionSql = "SELECT p.pieceType, p.team, pt.x, pt.y "
                                + "FROM piece p "
                                + "JOIN point pt ON p.pointId = pt.id";

        try (final var connection = databaseConnector.getConnection();
             final var positionStmt = connection.prepareStatement(positionSql);
             final ResultSet rs = positionStmt.executeQuery()) {

            while (rs.next()) {
                positions.add(mapToPosition(rs));
            }
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
        return positions;
    }

    private static Position mapToPosition(final ResultSet rs) throws SQLException {
        final PieceType pieceType = PieceType.valueOf(rs.getString("pieceType"));
        final int x = rs.getInt("x");
        final int y = rs.getInt("y");
        final Team team = Team.valueOf(rs.getString("team").toUpperCase());
        return Position.newInstance(Point.newInstance(x, y), PieceType.find(pieceType, team));
    }

    public void deleteAllPosition() {
        final var deletePieceSql = "DELETE FROM piece";
        final var deletePointSql = "DELETE FROM point";

        try (final var connection = databaseConnector.getConnection();
             final var deletePieceStmt = connection.prepareStatement(deletePieceSql);
             final var deletePointStmt = connection.prepareStatement(deletePointSql)) {
            deletePieceStmt.executeUpdate();
            deletePointStmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
