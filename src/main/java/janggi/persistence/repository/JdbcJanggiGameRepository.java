package janggi.persistence.repository;

import janggi.domain.Position;
import janggi.exception.PersistenceTimeoutException;
import janggi.persistence.GameStatus;
import janggi.persistence.dto.MoveHistory;
import janggi.persistence.model.JanggiGameHistory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.ArrayList;
import java.util.List;

public class JdbcJanggiGameRepository implements JanggiGameRepository {

    private static final String JDBC_URL = "jdbc:sqlite:janggi.db";
    private static final int QUERY_TIMEOUT_SECONDS = 3;
    private static final String FIND_RECENT_GAME_SQL =
        """
            SELECT id, status
            FROM janggi_game
            WHERE status = ?
            ORDER BY id DESC
            LIMIT 1
            """;
    private static final String FIND_MOVE_HISTORY_SQL =
        """
            SELECT turn_number, start_x, start_y, end_x, end_y
            FROM move_history
            WHERE game_id = ?
            ORDER BY turn_number ASC
            """;
    private static final String UPDATE_GAME_STATUS_SQL =
        """
            UPDATE janggi_game
            SET status = ?
            WHERE id = ?
            """;
    private static final String CREATE_SQL =
        """
            INSERT INTO janggi_game(status)
            VALUES (?)
            """;
    private static final String SAVE_MOVE_HISTORY_SQL =
        """
            INSERT INTO move_history(game_id, turn_number, start_x, start_y, end_x, end_y)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
    private static final String FIND_LAST_INSERTED_ID_SQL = "SELECT last_insert_rowid()";

    @Override
    public long createNewGame() {
        try (
            Connection connection = DriverManager.getConnection(JDBC_URL);
            PreparedStatement statement = connection.prepareStatement(CREATE_SQL)
        ) {
            statement.setString(1, GameStatus.IN_PROGRESS.name());
            statement.executeUpdate();
            return findLastInsertedId(connection);
        } catch (SQLException e) {
            throw new IllegalStateException("게임 생성에 실패했습니다.", e);
        }
    }

    @Override
    public void saveMove(long gameId, int turnNumber, Position startPosition, Position endPosition) {
        try (
            Connection connection = DriverManager.getConnection(JDBC_URL);
            PreparedStatement statement = connection.prepareStatement(SAVE_MOVE_HISTORY_SQL)
        ) {
            statement.setQueryTimeout(QUERY_TIMEOUT_SECONDS);
            statement.setLong(1, gameId);
            statement.setInt(2, turnNumber);
            statement.setInt(3, startPosition.getX());
            statement.setInt(4, startPosition.getY());
            statement.setInt(5, endPosition.getX());
            statement.setInt(6, endPosition.getY());
            statement.executeUpdate();
        } catch (SQLTimeoutException e) {
            throw new PersistenceTimeoutException("타임아웃입니다. 재시도합니다.", e);
        } catch (SQLException e) {
            throw new IllegalStateException("수 저장에 실패했습니다.", e);
        }
    }

    @Override
    public void update(GameStatus gameStatus, long gameId) {
        try (
            Connection connection = DriverManager.getConnection(JDBC_URL);
            PreparedStatement statement = connection.prepareStatement(UPDATE_GAME_STATUS_SQL)
        ) {
            statement.setQueryTimeout(QUERY_TIMEOUT_SECONDS);
            statement.setString(1, gameStatus.name());
            statement.setLong(2, gameId);
            statement.executeUpdate();
        } catch (SQLTimeoutException e) {
            throw new PersistenceTimeoutException("타임아웃입니다. 재시도합니다.", e);
        } catch (SQLException e) {
            throw new IllegalStateException("게임 상태 변경에 실패했습니다.", e);
        }
    }

    @Override
    public JanggiGameHistory findRecentGame() {
        try (
            Connection connection = DriverManager.getConnection(JDBC_URL);
            PreparedStatement recentGameStatement = connection.prepareStatement(FIND_RECENT_GAME_SQL)
        ) {
            recentGameStatement.setString(1, GameStatus.IN_PROGRESS.name());
            ResultSet recentGameResultSet = recentGameStatement.executeQuery();
            if (!recentGameResultSet.next()) {
                return JanggiGameHistory.createEmpty();
            }
            long gameId = recentGameResultSet.getLong("id");
            GameStatus gameStatus = GameStatus.valueOf(recentGameResultSet.getString("status"));
            List<MoveHistory> moveHistories = findMoveHistories(connection, FIND_MOVE_HISTORY_SQL, gameId);
            return new JanggiGameHistory(gameId, moveHistories, gameStatus);
        } catch (SQLException e) {
            throw new IllegalStateException("최근 게임 조회에 실패했습니다.", e);
        }
    }

    private long findLastInsertedId(Connection connection) throws SQLException {
        try (
            PreparedStatement statement = connection.prepareStatement(FIND_LAST_INSERTED_ID_SQL)
        ) {
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            return resultSet.getLong(1);
        }
    }

    private List<MoveHistory> findMoveHistories(Connection connection, String sql, long gameId) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            ResultSet resultSet = statement.executeQuery();

            List<MoveHistory> moveHistories = new ArrayList<>();
            while (resultSet.next()) {
                moveHistories.add(new MoveHistory(
                    resultSet.getInt("turn_number"),
                    resultSet.getInt("start_x"),
                    resultSet.getInt("start_y"),
                    resultSet.getInt("end_x"),
                    resultSet.getInt("end_y")
                ));
            }
            return moveHistories;
        }
    }
}
