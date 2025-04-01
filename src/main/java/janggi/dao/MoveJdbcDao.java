package janggi.dao;

import janggi.dto.MoveDto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MoveJdbcDao implements MoveDao {

    @Override
    public List<MoveDto> selectAllHistory(final int gameId) {
        final String selectAllQuery = "SELECT start_row, start_column, end_row, end_column FROM moveHistory WHERE game_id = ?";
        final List<MoveDto> moveDtos = new ArrayList<>();
        try (final Connection connection = ConnectionGenerator.getConnection();
             final var preparedStatement = connection.prepareStatement(selectAllQuery)) {
            preparedStatement.setInt(1, gameId);
            final ResultSet historyResult = preparedStatement.executeQuery();
            while (historyResult.next()) {
                moveDtos.add(makeHistory(historyResult));
            }
        } catch (SQLException e) {
            throw new RuntimeException("해당 게임의 움직인 기록들을 불러외지 못했습니다.");
        }
        return moveDtos;
    }

    @Override
    public void saveHistory(final MoveDto moveDto, final int gameId) {
        final String historySaveQuery = "INSERT INTO moveHistory(game_id, start_row, start_column, end_row, end_column) VALUES (?,?,?,?,?)";
        try (final Connection connection = ConnectionGenerator.getConnection();
             final var preparedStatement = connection.prepareStatement(historySaveQuery)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setString(2, moveDto.getStartRow());
            preparedStatement.setString(3, moveDto.getStartColumn());
            preparedStatement.setString(4, moveDto.getEndRow());
            preparedStatement.setString(5, moveDto.getEndColumn());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("움직인 기록을 저장하지 못했습니다.");
        }
    }

    private MoveDto makeHistory(final ResultSet historyResult) throws SQLException {
        final String startRow = historyResult.getString("start_row");
        final String startColumn = historyResult.getString("start_column");
        final String endRow = historyResult.getString("end_row");
        final String endColumn = historyResult.getString("end_column");
        return MoveDto.of(startRow, startColumn, endRow, endColumn);
    }
}
