package database.dao;

import database.dto.IntersectionDto;
import database.mapper.IntersectionRowMapper;
import domain.intersection.Intersection;
import java.sql.SQLException;
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

    public void saveAll(Long boardId, List<IntersectionDto> intersections) throws SQLException {
        jdbcTemplate.saveAll(INSERT_INTERSECTION_QUERY, boardId, intersections);
    }

    public List<Intersection> readByBoardId(Long boardId) throws SQLException{
        return jdbcTemplate.selectList(
                READ_ALL_INTERSECTION_QUERY,
                new IntersectionRowMapper(),
                boardId
        );
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

}
