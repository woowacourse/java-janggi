package janggi.dao;

import janggi.dto.MoveDto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JanggiJdbcDao implements JanggiDao {
    private static final String SERVER = "localhost:13306"; // MySQL 서버 주소
    private static final String DATABASE = "janggi"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    private final Connection connection;

    public JanggiJdbcDao() {
        this.connection = getConnection();
    }

    public Connection getConnection() {
        try {
            return DriverManager.getConnection("jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveInitialGame(final int setupOption) {
        final String gameSaveQuery = "INSERT INTO game(setup_option, finished) value (?, false)";
        try (final var preparedStatement = connection.prepareStatement(gameSaveQuery)) {
            preparedStatement.setInt(1, setupOption);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existNotFinishedGame() {
        final String notFinishedGameQuery = "SELECT * FROM game WHERE finished=0";
        try (final var preparedStatement = connection.prepareStatement(notFinishedGameQuery)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int findRecentNotFinishedGameId() {
        final String notFinishedGameQuery = "SELECT * FROM game WHERE finished = 0 ORDER BY id DESC";
        try (final var preparedStatement = connection.prepareStatement(notFinishedGameQuery)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
            throw new IllegalStateException();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Integer> findNotFinishedGameIds() {
        final String notFinishedGameQuery = "SELECT * FROM game WHERE finished=0";
        final List<Integer> ids = new ArrayList<>();
        try (final var preparedStatement = connection.prepareStatement(notFinishedGameQuery)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                ids.add(resultSet.getInt("id"));
            }
            return ids;
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int findGameSetup(final int gameId) {
        final String notFinishedGameQuery = "SELECT * FROM game WHERE id=?";
        try (final var preparedStatement = connection.prepareStatement(notFinishedGameQuery)) {
            preparedStatement.setInt(1, gameId);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("setup_option");
            }
            throw new IllegalStateException();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<MoveDto> selectAllHistory(final int gameId) {
        final String selectAllQuery = "SELECT start_row, start_column, end_row, end_column FROM moveHistory WHERE game_id = ?";
        final List<MoveDto> moveDtos = new ArrayList<>();
        try (final var preparedStatement = connection.prepareStatement(selectAllQuery)) {
            preparedStatement.setInt(1, gameId);
            final ResultSet historyResult = preparedStatement.executeQuery();
            while (historyResult.next()) {
                moveDtos.add(makeHistory(historyResult));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return moveDtos;
    }

    @Override
    public void saveHistory(final MoveDto moveDto, final int gameId) {
        final String historySaveQuery = "INSERT INTO moveHistory(game_id, start_row, start_column, end_row, end_column) VALUES (?,?,?,?,?)";
        try (final var preparedStatement = connection.prepareStatement(historySaveQuery)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.setString(2, moveDto.getStartRow());
            preparedStatement.setString(3, moveDto.getStartColumn());
            preparedStatement.setString(4, moveDto.getEndRow());
            preparedStatement.setString(5, moveDto.getEndColumn());
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setGameFinished(final int gameId) {
        final String gameSetFinishedQuery = "UPDATE game SET finished = true WHERE id = ?";
        try (final var preparedStatement = connection.prepareStatement(gameSetFinishedQuery)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
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
