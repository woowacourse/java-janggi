package database.dao;

import database.dto.BoardSummaryDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcBoardDao implements BoardDao{

    private static final String INSERT_BOARD_QUERY = """
            insert into board () values ();
            """;

    private static final String READ_PLAYING_BOARD_QUERY = """
            select id, current_turn, is_finished
            from board
            where is_finished = false;
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
    public List<BoardSummaryDto> readPlayingJanggiBoard(Connection connection) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(READ_PLAYING_BOARD_QUERY)) {
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


}
