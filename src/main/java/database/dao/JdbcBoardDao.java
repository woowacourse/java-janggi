package database.dao;

import database.dto.BoardSummaryDto;
import database.dto.GameResult;
import database.mapper.SummaryDtoRowMapper;
import domain.piece.Team;

import java.sql.*;
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
    public Long save() throws SQLException {
        return jdbcTemplate.save(INSERT_BOARD_QUERY);
    }


    @Override
    public List<BoardSummaryDto> readAllNotFinished() throws SQLException {
        return jdbcTemplate.selectList(
                READ_PLAYING_BOARD_LIST_QUERY,
                new SummaryDtoRowMapper()
        );
    }

    public Optional<BoardSummaryDto> readPlayingById(Long boardId) throws SQLException {
        BoardSummaryDto result = jdbcTemplate.selectOne(
                READ_BOARD_QUERY,
                new SummaryDtoRowMapper(),
                boardId
        );
        return Optional.ofNullable(result);
    }

    @Override
    public void updateTurn(Long boardId, Team nextTurn) throws SQLException {
        jdbcTemplate.update(
                UPDATE_BOARD_QUERY,
                nextTurn.name(),
                boardId
        );
    }

    @Override
    public void updateResult(Long boardId, GameResult gameResult) throws SQLException {
        jdbcTemplate.update(
                UPDATE_BOARD_RESULT_QUERY,
                gameResult.winner().name(),
                gameResult.hanScore(),
                gameResult.choScore(),
                boardId
        );
    }

}
