package database.mapper;

import database.dto.BoardSummaryDto;

import java.sql.ResultSet;
import java.sql.SQLException;

// THINK 람다
public class SummaryDtoRowMapper implements RowMapper<BoardSummaryDto> {

    @Override
    public BoardSummaryDto map(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        String currentTeam = resultSet.getString("current_turn");
        boolean isFinished = resultSet.getBoolean("is_finished");
        return new BoardSummaryDto(id, currentTeam, isFinished);
    }

}
