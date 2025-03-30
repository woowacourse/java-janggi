package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public final class GameDAO {
    private final Connector connector;
    private final AtomicInteger counter = new AtomicInteger(0);

    public GameDAO(final Connector connector) {
        this.connector = Objects.requireNonNull(connector, "connecter가 null일 수 없습니다.");
        getNextId();
    }

    public int create() {
        final String query = "INSERT INTO Game (is_active) VALUES (true)";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
            return counter.incrementAndGet();
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 게임을 생성하는 데 실패했습니다.");
        }
    }

    public void deactivate(final int gameId) {
        final String query = "UPDATE Game SET is_active = false WHERE id = ?";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 게임을 비활성화하는 데 실패했습니다.");
        }
    }

    public boolean existsActiveGameById(final int gameId) {
        final String query = "SELECT EXISTS(SELECT 1 FROM Game WHERE id = ? AND is_active=true)";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);

            final ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next() && resultSet.getBoolean(1);
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 활성화된 게임를 조회하는 데 실패했습니다.");
        }
    }

    private void getNextId() {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT MAX(id) AS last_id FROM game");
             ResultSet resultSet = preparedStatement.executeQuery()) {
            incrementLastId(resultSet);
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 게임 ID를 초기화하는 데 실패했습니다.");
        }
    }

    private void incrementLastId(final ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            counter.addAndGet(resultSet.getInt("last_id"));
        }
    }
}
