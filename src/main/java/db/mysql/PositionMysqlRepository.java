package db.mysql;

import db.PositionRepository;
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

public class PositionMysqlRepository implements PositionRepository {
    @Override
    public void savePosition(final Connection connection, final Position position) {
        final Team team = determaineTeam(position);

        final var insertPointSql = "INSERT INTO point(x, y) VALUES(?, ?)";
        final var insertPieceSql = "INSERT INTO piece(pieceType, team, pointId) VALUES(?, ?, ?)";

        try (final var pointStmt = connection.prepareStatement(insertPointSql, Statement.RETURN_GENERATED_KEYS);
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
            throw new RuntimeException("SQL쿼리에 문제가 존재합니다.", e);
        }
    }

    private Team determaineTeam(final Position position) {
        if (position.isGreenTeam()) {
            return Team.GREEN;
        }
        return Team.RED;
    }

    @Override
    public List<Position> getPositions(final Connection connection) {
        final List<Position> positions = new ArrayList<>();
        final var positionSql = """
                SELECT P.pieceType, P.team, PT.x, PT.y 
                FROM piece P 
                JOIN point PT on P.pointId = PT.id
                """;

        try (final var positionStmt = connection.prepareStatement(positionSql);
             final ResultSet rs = positionStmt.executeQuery()) {

            while (rs.next()) {
                final PieceType pieceType = PieceType.valueOf(rs.getString("pieceType"));
                final int x = rs.getInt("x");
                final int y = rs.getInt("y");
                final Team team = Team.valueOf(rs.getString("team").toUpperCase());
                final Position position = Position.newInstance(Point.newInstance(x, y),
                        PieceType.find(pieceType, team));
                positions.add(position);
            }

            return positions;
        } catch (final SQLException e) {
            throw new RuntimeException("SQL쿼리에 문제가 존재합니다." + e);
        }
    }

    @Override
    public void updatePosition(final Connection connection, final PointValue fromPoint, final PointValue toPoint) {
        final var findPointSql = "SELECT id FROM point WHERE x = ? AND y = ?";
        final var updatePointSql = "UPDATE point SET x = ?, y = ? WHERE id = ?";

        try (final var findPointStmt = connection.prepareStatement(findPointSql);
             final var updatePointStmt = connection.prepareStatement(updatePointSql)) {

            findPointStmt.setInt(1, fromPoint.x());
            findPointStmt.setInt(2, fromPoint.y());
            final ResultSet resultSet = findPointStmt.executeQuery();

            if (resultSet.next()) {
                final int pointId = resultSet.getInt("id");
                updatePointStmt.setInt(1, toPoint.x());
                updatePointStmt.setInt(2, toPoint.y());
                updatePointStmt.setInt(3, pointId);
                updatePointStmt.executeUpdate();
            }
        } catch (final SQLException | IllegalArgumentException e) {
            throw new RuntimeException("SQL쿼리에 문제가 존재합니다.", e);
        }
    }

    @Override
    public void deletePosition(final Connection connection, final PointValue pointValue) {
        final var findPointSql = "select id from point where x = ? and y = ?";
        final var deletePieceSql = "delete from piece where pointId = ?";
        final var deletePointSql = "delete from point where id = ?";

        try (final var findPointStmt = connection.prepareStatement(findPointSql);
             final var deletePieceStmt = connection.prepareStatement(deletePieceSql);
             final var deletePointStmt = connection.prepareStatement(deletePointSql)) {

            findPointStmt.setInt(1, pointValue.x());
            findPointStmt.setInt(2, pointValue.y());
            final ResultSet resultSet = findPointStmt.executeQuery();
            if (resultSet.next()) {
                final int pointId = resultSet.getInt("id");
                deletePieceStmt.setInt(1, pointId);
                deletePieceStmt.executeUpdate();
                deletePointStmt.setInt(1, pointId);
                deletePointStmt.executeUpdate();
            }
        } catch (final SQLException | IllegalArgumentException e) {
            throw new RuntimeException("SQL쿼리에 문제가 존재합니다." + e);
        }
    }

    @Override
    public void deleteAllPosition(final Connection connection) {
        final var deletePieceSql = "delete from piece";
        final var deletePointSql = "delete from point";

        try (final var deletePieceStmt = connection.prepareStatement(deletePieceSql);
             final var deletePointStmt = connection.prepareStatement(deletePointSql)) {
            deletePieceStmt.executeUpdate();
            deletePointStmt.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("SQL쿼리에 문제가 존재합니다." + e);
        }
    }

    @Override
    public void updatePoint(final Connection connection, final PointValue fromPoint, final PointValue toPoint) {
        final var findPointSql = "SELECT id FROM point WHERE x = ? AND y = ?";
        final var updatePointSql = "UPDATE point SET x = ?, y = ? WHERE id = ?";

        try (final var findPointStmt = connection.prepareStatement(findPointSql);
             final var updatePointStmt = connection.prepareStatement(updatePointSql)) {

            findPointStmt.setInt(1, fromPoint.x());
            findPointStmt.setInt(2, fromPoint.y());
            final ResultSet resultSet = findPointStmt.executeQuery();

            if (resultSet.next()) {
                final int pointId = resultSet.getInt("id");
                updatePointStmt.setInt(1, toPoint.x());
                updatePointStmt.setInt(2, toPoint.y());
                updatePointStmt.setInt(3, pointId);
                updatePointStmt.executeUpdate();
            }
        } catch (final SQLException | IllegalArgumentException e) {
            throw new RuntimeException("SQL쿼리에 문제가 존재합니다.", e);
        }
    }
}
