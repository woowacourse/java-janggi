package janggi.repository.dao;

import janggi.repository.entity.GameEntity;
import janggi.repository.util.JdbcQueryExecutor;
import janggi.repository.util.transaction.TransactionManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class GameDao {

    private final JdbcQueryExecutor jdbcQueryExecutor;

    public GameDao(TransactionManager transactionManager) {
        jdbcQueryExecutor = new JdbcQueryExecutor(transactionManager);
    }

    public Long insert(GameEntity gameEntity) {
        String sql = "INSERT INTO game (turn, is_active) VALUES (?, ?)";
        return jdbcQueryExecutor.insert(sql, gameEntity.getTurn(), gameEntity.isActive());
    }

    public Optional<GameEntity> findById(Long id) {
        String sql = "SELECT id, turn, is_active FROM game WHERE id = ?";
        return jdbcQueryExecutor.queryForObject(sql, this::mapToGameEntity, id);
    }

    public List<GameEntity> findActiveGames() {
        String sql = "SELECT id, turn, is_active FROM game WHERE is_active = true";
        return jdbcQueryExecutor.query(sql, this::mapToGameEntity);
    }

    public void updateTurn(Long id, String turn) {
        String sql = "UPDATE game SET turn = ? WHERE id = ?";
        jdbcQueryExecutor.update(sql, turn, id);
    }

    public void updateIsActive(Long id, boolean isActive) {
        String sql = "UPDATE game SET is_active = ? WHERE id = ?";
        jdbcQueryExecutor.update(sql, isActive, id);
    }

    private GameEntity mapToGameEntity(ResultSet rs) throws SQLException {
        return new GameEntity(
                rs.getLong("id"),
                rs.getString("turn"),
                rs.getBoolean("is_active"));
    }
}
