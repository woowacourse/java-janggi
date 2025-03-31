package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        final String query = "INSERT INTO Game (is_activate) VALUES (true)";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
            return counter.incrementAndGet();
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 게임을 생성하는 데 실패했습니다: " + e);
        }
    }

    public void deactivate(final int gameId) {
        final String query = "UPDATE Game SET is_activate = false WHERE id = ?";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, gameId);
            preparedStatement.executeUpdate();
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 게임을 비활성화하는 데 실패했습니다: " + e);
        }
    }

    private void getNextId() {
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(
                     "SELECT MAX(id) AS last_id FROM game");
             final ResultSet resultSet = preparedStatement.executeQuery()) {
            incrementLastId(resultSet);
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 게임 ID를 초기화하는 데 실패했습니다: " + e);
        }
    }

    private void incrementLastId(final ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            counter.addAndGet(resultSet.getInt("last_id"));
        }
    }

    public List<Integer> findAllActivateGames() {
        final String query = "SELECT game.id FROM game WHERE is_activate=true";
        try (final Connection connection = connector.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            final ResultSet resultSet = preparedStatement.executeQuery();
            return convertResultSetToGameIds(resultSet);
        } catch (final SQLException e) {
            throw new RuntimeException("데이터베이스에서 복수의 플레이어를 조회하는 데 실패했습니다: " + e);
        }
    }

    private List<Integer> convertResultSetToGameIds(final ResultSet resultSet) throws SQLException {
        final List<Integer> gameIds = new ArrayList<>();
        while (resultSet.next()) {
            gameIds.add(resultSet.getInt("id"));
        }
        return gameIds;
    }
}
