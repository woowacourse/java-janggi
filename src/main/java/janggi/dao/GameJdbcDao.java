package janggi.dao;

import janggi.dto.MoveDto;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameJdbcDao implements GameDao {

    @Override
    public void saveInitialGame(final int setupOption) {
        final String gameSaveQuery = "INSERT INTO game(setup_option, finished) value (?, false)";
        try (final Connection connection = ConnectionGenerator.getConnection();
             final var preparedStatement = connection.prepareStatement(gameSaveQuery)) {
            preparedStatement.setInt(1, setupOption);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("초기 게임 상태가 저장되지 않았습니다.");
        }
    }

    @Override
    public boolean existNotFinishedGame() {
        final String notFinishedGameQuery = "SELECT * FROM game WHERE finished=0";
        try (final Connection connection = ConnectionGenerator.getConnection();
             final var preparedStatement = connection.prepareStatement(notFinishedGameQuery)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException("게임 기록을 조회하지 못했습니다.");
        }
    }

    @Override
    public int findRecentNotFinishedGameId() {
        final String notFinishedGameQuery = "SELECT * FROM game WHERE finished = 0 ORDER BY id DESC";
        try (final Connection connection = ConnectionGenerator.getConnection();
             final var preparedStatement = connection.prepareStatement(notFinishedGameQuery)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            throw new IllegalStateException();
        } catch (final SQLException e) {
            throw new RuntimeException("가장 최근 게임 기록을 조회하지 못했습니다.");
        }
    }

    @Override
    public List<Integer> findNotFinishedGameIds() {
        final String notFinishedGameQuery = "SELECT * FROM game WHERE finished=0";
        final List<Integer> ids = new ArrayList<>();
        try (final Connection connection = ConnectionGenerator.getConnection();
             final var preparedStatement = connection.prepareStatement(notFinishedGameQuery)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getInt("id"));
            }
            return ids;
        } catch (final SQLException e) {
            throw new RuntimeException("게임 기록을 조회하지 못했습니다.");
        }
    }

    @Override
    public int findGameSetup(final int gameId) {
        final String notFinishedGameQuery = "SELECT * FROM game WHERE id=?";
        try (final Connection connection = ConnectionGenerator.getConnection();
             final var preparedStatement = connection.prepareStatement(notFinishedGameQuery)) {
            preparedStatement.setInt(1, gameId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("setup_option");
            }
            throw new IllegalStateException("게임 기록이 없습니다.");
        } catch (final SQLException e) {
            throw new RuntimeException("해당 id로 게임을 찾지 못했습니다.");
        }
    }

//    @Override
//    public List<MoveDto> selectAllHistory(final int gameId) {
//        final String selectAllQuery = "SELECT start_row, start_column, end_row, end_column FROM moveHistory WHERE game_id = ?";
//        final List<MoveDto> moveDtos = new ArrayList<>();
//        try (final Connection connection = ConnectionGenerator.getConnection();
//             final var preparedStatement = connection.prepareStatement(selectAllQuery)) {
//            preparedStatement.setInt(1, gameId);
//            final ResultSet historyResult = preparedStatement.executeQuery();
//            while (historyResult.next()) {
//                moveDtos.add(makeHistory(historyResult));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException("해당 게임의 움직인 기록들을 불러외지 못했습니다.");
//        }
//        return moveDtos;
//    }
//
//    @Override
//    public void saveHistory(final MoveDto moveDto, final int gameId) {
//        final String historySaveQuery = "INSERT INTO moveHistory(game_id, start_row, start_column, end_row, end_column) VALUES (?,?,?,?,?)";
//        try (final Connection connection = ConnectionGenerator.getConnection();
//             final var preparedStatement = connection.prepareStatement(historySaveQuery)) {
//            preparedStatement.setInt(1, gameId);
//            preparedStatement.setString(2, moveDto.getStartRow());
//            preparedStatement.setString(3, moveDto.getStartColumn());
//            preparedStatement.setString(4, moveDto.getEndRow());
//            preparedStatement.setString(5, moveDto.getEndColumn());
//            preparedStatement.executeUpdate();
//        } catch (final SQLException e) {
//            throw new RuntimeException("움직인 기록을 저장하지 못했습니다.");
//        }
//    }

    @Override
    public void setGameFinished(final int gameId) {
        final String gameSetFinishedQuery = "UPDATE game SET finished = true WHERE id = ?";
        try (final Connection connection = ConnectionGenerator.getConnection();
             final var preparedStatement = connection.prepareStatement(gameSetFinishedQuery)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("새로운 게임 기록을 추가하지 못했습니다.");
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
