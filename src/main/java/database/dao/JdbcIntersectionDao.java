package database.dao;

import domain.intersection.Intersection;
import domain.intersection.IntersectionType;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Team;
import domain.point.Point;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcIntersectionDao implements IntersectionDao {

    private static final String INSERT_INTERSECTION_QUERY = """
            insert into intersection (board_id, y, x, piece_type, team, intersection_type) 
            values (?, ?, ?, ?, ?, ?)
            """;

    private static final String READ_ALL_INTERSECTION_QUERY = """
            select y, x, piece_type, team, intersection_type 
            from intersection 
            where board_id = ?
            """;

    private static final String UPDATE_INTERSECTION_QUERY = """
            update intersection
            set piece_type = ?, team = ?
            where board_id = ? and y = ? and x = ?
            """;

    @Override
    public void saveAllIntersection(Connection connection, Long boardId, List<Intersection> intersections) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTERSECTION_QUERY)) {

            for (Intersection intersection : intersections) {
                Point point = intersection.getPoint();
                Piece piece = intersection.readPiece();
                preparedStatement.setString(1, String.valueOf(boardId));
                preparedStatement.setString(2, String.valueOf(point.y()));
                preparedStatement.setString(3, String.valueOf(point.x()));
                preparedStatement.setString(4, piece.pieceType().name());
                preparedStatement.setString(5, piece.team().name());
                preparedStatement.setString(6, intersection.readIntersectionType().name());

                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Intersection> readIntersectionByBoardId(Connection connection, Long boardId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_INTERSECTION_QUERY)) {
            preparedStatement.setString(1, String.valueOf(boardId));

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Intersection> intersections = new ArrayList<>();

            while (resultSet.next()) {
                int y = resultSet.getInt("y");
                int x = resultSet.getInt("x");

                String pieceTypeName = resultSet.getString("piece_type");
                String teamName = resultSet.getString("team");
                String intersectionTypeName = resultSet.getString("intersection_type");

                Point point = new Point(y, x);
                Piece piece = new Piece(Team.valueOf(teamName), PieceType.valueOf(pieceTypeName));
                IntersectionType type = IntersectionType.valueOf(intersectionTypeName);
                Intersection intersection = type.create(point, piece);
                intersections.add(intersection);
            }

            return intersections;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void update(Connection connection, Long boardId, Intersection intersection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_INTERSECTION_QUERY)) {

            Piece piece = intersection.readPiece();
            String teamName = piece.team().name();
            String pieceTypeName = piece.pieceType().name();

            Point point = intersection.getPoint();

            preparedStatement.setString(1, pieceTypeName);
            preparedStatement.setString(2, teamName);
            preparedStatement.setString(3, String.valueOf(boardId));
            preparedStatement.setString(4, String.valueOf(point.y()));
            preparedStatement.setString(5, String.valueOf(point.x()));

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new RuntimeException("업데이트할 인터섹션을 찾을 수 없습니다");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
