package janggi.infrastructure.repository;

import janggi.config.DBConnection;
import janggi.domain.game.GameStatus;
import janggi.infrastructure.entity.GameEntity;
import janggi.infrastructure.entity.EntityMapper;
import java.util.List;
import java.util.Optional;

public class GameRepositoryImpl implements GameRepository {

    private static final String TABLE_NAME = "games";
    private static final EntityMapper<GameEntity> ENTITY_MAPPER = resultSet -> {
        long id = resultSet.getLong(1);
        String name = resultSet.getString(2);
        int turnsTaken = resultSet.getInt(3);
        String teamQueue = resultSet.getString(4);
        String status = resultSet.getString(5);
        return new GameEntity(id, name, turnsTaken, teamQueue, status);
    };

    private final DBConnection dbConnection;

    public GameRepositoryImpl(final DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public long save(final GameEntity gameEntity) {
        final String sql = String.format(
            "INSERT INTO %s (name, turns_taken, team_queue, status) VALUES (?, ?, ?, ?)",
            TABLE_NAME);
        return dbConnection.executeUpdate(sql, gameEntity.name(), gameEntity.turnsTaken(),
            gameEntity.teamQueue(), gameEntity.status());
    }

    @Override
    public List<Long> findByStatusOrderByLatest(final GameStatus gameStatus, final int limit) {
        final String sql = String.format(
            "SELECT id FROM %s WHERE status = ? ORDER BY created_at DESC LIMIT ?", TABLE_NAME);
        final EntityMapper<Long> mapper = resultSet -> resultSet.getLong(1);

        return dbConnection.executeSelectAll(sql, mapper, gameStatus.name(), limit);
    }

    @Override
    public Optional<GameEntity> findById(final long targetId) {
        final String sql = String.format(
            "SELECT id, name, turns_taken, team_queue, status FROM %s WHERE id = ?",
            TABLE_NAME);

        return dbConnection.executeSelect(sql, ENTITY_MAPPER, targetId);
    }

    @Override
    public long updateById(final long id, final GameEntity gameEntity) {
        final String sql = String.format(
            "UPDATE %s SET turns_taken = ?, team_queue = ? WHERE id = ?",
            TABLE_NAME);

        return dbConnection.executeUpdate(sql, gameEntity.turnsTaken(), gameEntity.teamQueue(),
            id);
    }

    @Override
    public long updateStatusById(long id, GameStatus gameStatus) {
        final String sql = String.format("UPDATE %s SET status = ? WHERE id = ?", TABLE_NAME);

        return dbConnection.executeUpdate(sql, gameStatus.name(), id);
    }
}
