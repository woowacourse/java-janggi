package database.dao;

import database.dto.BoardSummaryDto;
import database.dto.GameResult;
import domain.piece.Team;

import java.util.List;
import java.util.Optional;

public class JdbcBoardDao implements BoardDao {

    private static final String INSERT_BOARD_QUERY = """
            insert into board () values ();
            """;

    private static final String READ_PLAYING_BOARD_LIST_QUERY = """
            select id, current_turn, is_finished
            from board
            where is_finished = false;
            """;

    private static final String READ_BOARD_QUERY = """
            select id, current_turn, is_finished
            from board
            where id = ?;
            """;

    private static final String UPDATE_BOARD_QUERY = """
            UPDATE board
            SET current_turn = ?
            WHERE id = ?
            """;

    private static final String UPDATE_BOARD_RESULT_QUERY = """
            UPDATE board
            SET winner = ?, han_score = ?, cho_score = ? , is_finished = true
            WHERE id = ?;
            """;

    private final JdbcTemplate jdbcTemplate;

    public JdbcBoardDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save() {
        return jdbcTemplate.save(INSERT_BOARD_QUERY);
    }


    @Override
    public List<BoardSummaryDto> readAllNotFinished() {
        return jdbcTemplate.selectList(
                READ_PLAYING_BOARD_LIST_QUERY,
                resultSet -> new BoardSummaryDto(
                        resultSet.getLong("id"),
                        resultSet.getString("current_turn"),
                        resultSet.getBoolean("is_finished")
                )
        );
    }

    public Optional<BoardSummaryDto> readPlayingById(Long boardId) {
        BoardSummaryDto result = jdbcTemplate.selectOne(
                READ_BOARD_QUERY,
                resultSet -> new BoardSummaryDto(
                        resultSet.getLong("id"),
                        resultSet.getString("current_turn"),
                        resultSet.getBoolean("is_finished")
                ),
                boardId
        );
        return Optional.ofNullable(result);
    }

    @Override
    public void updateTurn(Long boardId, Team nextTurn) {
        jdbcTemplate.update(
                UPDATE_BOARD_QUERY,
                nextTurn.name(),
                boardId
        );
    }

    @Override
    public void updateResult(Long boardId, GameResult gameResult) {
        jdbcTemplate.update(
                UPDATE_BOARD_RESULT_QUERY,
                gameResult.winner().name(),
                gameResult.hanScore(),
                gameResult.choScore(),
                boardId
        );
    }

}
