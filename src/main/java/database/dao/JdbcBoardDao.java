package database.dao;

import database.dto.BoardSummaryDto;
import domain.piece.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    @Override
    public Long save(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_BOARD_QUERY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<BoardSummaryDto> readPlayingJanggiBoardList(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(READ_PLAYING_BOARD_LIST_QUERY)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            List<BoardSummaryDto> boardSummaryDtos = new ArrayList<>();

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String currentTeam = resultSet.getString("current_turn");
                boolean isFinished = resultSet.getBoolean("is_finished");

                BoardSummaryDto boardSummaryDto = new BoardSummaryDto(id, currentTeam, isFinished);
                boardSummaryDtos.add(boardSummaryDto);
            }

            return boardSummaryDtos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public BoardSummaryDto readPlayingJanggiBoard(Connection connection, Long boardId) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(READ_BOARD_QUERY)) {
            preparedStatement.setString(1, String.valueOf(boardId));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                String currentTeam = resultSet.getString("current_turn");
                boolean isFinished = resultSet.getBoolean("is_finished");

                return new BoardSummaryDto(id, currentTeam, isFinished);
            }

            throw new IllegalArgumentException("해당 ID의 Board를 찾을 수 없습니다.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBoardTurn(Connection connection, Long boardId, Team nextTurn) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_BOARD_QUERY)) {

            preparedStatement.setString(1, nextTurn.name());
            preparedStatement.setLong(2, boardId);

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("장기판 업데이트에 실패했습니다.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
