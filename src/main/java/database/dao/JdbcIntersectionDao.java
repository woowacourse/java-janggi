package database.dao;

import database.dto.IntersectionDto;
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

    private final JdbcTemplate jdbcTemplate;

    public JdbcIntersectionDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveAll(Connection connection, Long boardId, List<IntersectionDto> intersections) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_INTERSECTION_QUERY)) {

            for (IntersectionDto dto : intersections) {
                setParameters(
                        preparedStatement,
                        boardId, dto.y(), dto.x(), dto.pieceType(), dto.teamName(), dto.intersectionType()
                );
                preparedStatement.addBatch();
            }

            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Intersection> readByBoardId(Connection connection, Long boardId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(READ_ALL_INTERSECTION_QUERY)) {
            setParameters(preparedStatement, boardId);

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
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void update(Long boardId, IntersectionDto intersection) throws SQLException {
        jdbcTemplate.update(
                UPDATE_INTERSECTION_QUERY,
                intersection.pieceType(),
                intersection.teamName(),
                boardId,
                intersection.y(),
                intersection.x()
        );
    }

    public void setParameters(PreparedStatement preparedStatement, Object... parameters) throws SQLException {
        for (int i = 0; i < parameters.length; i++) {
            preparedStatement.setObject(i + 1, parameters[i]);
        }
    }

}
