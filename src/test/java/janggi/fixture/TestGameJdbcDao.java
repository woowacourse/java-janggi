package janggi.fixture;

import janggi.dao.GameDao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TestGameJdbcDao implements GameDao {
    private static final String SERVER = "localhost:13307"; // MySQL 서버 주소
    private static final String DATABASE = "janggiTest"; // MySQL DATABASE 이름
    private static final String OPTION = "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USERNAME = "root"; //  MySQL 서버 아이디
    private static final String PASSWORD = "root"; // MySQL 서버 비밀번호

    private final Connection connection;

    public TestGameJdbcDao() {
        this.connection = getConnection();
    }

    private Connection getConnection() {
        try {
            final Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://" + SERVER + "/" + DATABASE + OPTION, USERNAME, PASSWORD);
            connection.setAutoCommit(false);
            return connection;
        } catch (final SQLException e) {
            System.err.println("DB 연결 오류:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void saveInitialGame(final int setupOption) {
        final String gameSaveQuery = "INSERT INTO testGame(setup_option, finished) value (?,false)";
        try (final var preparedStatement = connection.prepareStatement(gameSaveQuery)) {
            preparedStatement.setInt(1, setupOption);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existNotFinishedGame() {
        final String notFinishedGameQuery = "SELECT * FROM testGame WHERE finished=0";
        try (final var preparedStatement = connection.prepareStatement(notFinishedGameQuery)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int findRecentNotFinishedGameId() {
        final String notFinishedGameQuery = "SELECT * FROM testGame WHERE finished = 0 ORDER BY id DESC";
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
        final String notFinishedGameQuery = "SELECT * FROM testGame WHERE finished=0";
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
        final String notFinishedGameQuery = "SELECT * FROM testGame WHERE id=?";
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
    public void setGameFinished(final int gameId) {
        final String gameSetFinishedQuery = "UPDATE testGame SET finished = true WHERE id = ?";
        try (final var preparedStatement = connection.prepareStatement(gameSetFinishedQuery)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void rollBack() throws SQLException {
        connection.rollback();
        connection.close();
    }
}
