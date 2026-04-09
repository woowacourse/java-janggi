package janggi.repository.jdbc;

import janggi.database.DatabaseConnection;
import janggi.model.Team;
import janggi.repository.GameRepository;
import janggi.service.TransactionManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcGameRepository implements GameRepository {
    private final TransactionManager transactionManager;

    public JdbcGameRepository(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public Long save(Team currentTurn, String name) {
        String sql = "INSERT INTO game (current_turn, name) VALUES (?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, currentTurn.name());
            statement.setString(2, name);
            statement.executeUpdate();
            return getGeneratedKey(statement);
        } catch (SQLException e) {
            throw new IllegalStateException("DB 오류가 발생했습니다.", e);
        }
    }

    private Long getGeneratedKey(PreparedStatement statement) throws SQLException {
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getLong(1);
        }
        throw new IllegalStateException("게임 저장에 실패했습니다.");
    }

    public List<String> findAllNames() {
        String sql = "SELECT name FROM game";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            return getNames(statement.executeQuery());
        } catch (SQLException e) {
            throw new IllegalStateException("DB 오류가 발생했습니다.", e);
        }
    }

    private List<String> getNames(ResultSet resultSet) throws SQLException {
        List<String> names = new ArrayList<>();
        while (resultSet.next()) {
            names.add(resultSet.getString("name"));
        }
        return names;
    }

    public Optional<Long> findIdByName(String name) {
        String sql = "SELECT id FROM game WHERE name = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(resultSet.getLong("id"));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException("DB 오류가 발생했습니다.", e);
        }
    }

    public Optional<Team> findCurrentTurn(Long gameId) {
        String sql = "SELECT current_turn FROM game WHERE id = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, gameId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Team.valueOf(resultSet.getString("current_turn")));
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new IllegalStateException("DB 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void updateCurrentTurn(Long gameId, Team currentTurn) {
        String sql = "UPDATE game SET current_turn = ? WHERE id = ?";
        try (PreparedStatement statement = transactionManager.getConnection().prepareStatement(sql)) {
            statement.setString(1, currentTurn.name());
            statement.setLong(2, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("DB 오류가 발생했습니다.", e);
        }
    }

    @Override
    public void delete(Long gameId) {
        String sql = "DELETE FROM game WHERE id = ?";
        try (PreparedStatement statement = transactionManager.getConnection().prepareStatement(sql)) {
            statement.setLong(1, gameId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException("DB 오류가 발생했습니다.", e);
        }
    }
}
