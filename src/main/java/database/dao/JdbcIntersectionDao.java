package database.dao;

import database.mapper.JanggiBoardMapper;
import domain.intersection.Intersection;

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

    public void saveAll(Long boardId, List<Intersection> intersections) {
        jdbcTemplate.saveAll(
                INSERT_INTERSECTION_QUERY,
                intersections,
                (ps, intersection) -> {
                    ps.setLong(1, boardId);
                    ps.setInt(2, intersection.getPoint().y());
                    ps.setInt(3, intersection.getPoint().x());
                    ps.setString(4, intersection.getPiece().pieceType().name());
                    ps.setString(5, intersection.getPiece().team().name());
                    ps.setString(6, intersection.getType().name());
                }
        );
    }

    public List<Intersection> readByBoardId(Long boardId) {
        return jdbcTemplate.selectList(
                READ_ALL_INTERSECTION_QUERY,
                resultSet -> JanggiBoardMapper.toIntersection(
                        resultSet.getInt("y"),
                        resultSet.getInt("x"),
                        resultSet.getString("piece_type"),
                        resultSet.getString("team"),
                        resultSet.getString("intersection_type")
                ),
                boardId
        );
    }

    public void update(Long boardId, Intersection intersection) {
        jdbcTemplate.update(
                UPDATE_INTERSECTION_QUERY,
                intersection.getPiece().pieceType().name(),
                intersection.getPiece().team().name(),
                boardId,
                intersection.getPoint().y(),
                intersection.getPoint().x()
        );
    }

}
