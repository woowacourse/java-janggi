package janggi.repository;

import janggi.config.DBConnection;
import janggi.entity.GameEntity;
import janggi.global.EntityMapper;
import java.util.List;
import java.util.Optional;

public class GameRepositoryImpl implements GameRepository {

    private static final String TABLE_NAME = "games";

    private final DBConnection dbConnection;

    public GameRepositoryImpl(final DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public GameEntity save(final GameEntity gameEntity) {
        final String sql = String.format(
            "INSERT INTO %s (name, turns_taken, team_queue) VALUES ('%s', %d, '%s')",
            TABLE_NAME, gameEntity.name(), gameEntity.turns_taken(), gameEntity.team_queue());
        final long generatedId = dbConnection.executeUpdate(sql).getFirst();
        return new GameEntity(generatedId, gameEntity.name(), gameEntity.turns_taken(),
            gameEntity.team_queue());
    }

    @Override
    public boolean existsById(final long id) {
        return findById(id).isPresent();
    }

    @Override
    public List<Long> findAllIdsOrderByLatest(final int limit) {
        final String sql = String.format("SELECT id FROM %s ORDER BY created_at DESC LIMIT ?",
            TABLE_NAME);

        return dbConnection.executeSelectForIds(sql, limit);
    }

    @Override
    public Optional<GameEntity> findById(final long targetId) {
        final String sql = String.format(
            "SELECT id, name, turns_taken, team_queue FROM %s WHERE id = %d",
            TABLE_NAME, targetId);
        final EntityMapper<GameEntity> mapper = resultSet -> {
            long id = resultSet.getLong(1);
            String name = resultSet.getString(2);
            int turnsTaken = resultSet.getInt(3);
            String teamQueue = resultSet.getString(4);
            return new GameEntity(id, name, turnsTaken, teamQueue);
        };
        return dbConnection.executeSelect(sql, mapper);
    }

    @Override
    public long updateById(final long id, final GameEntity gameEntity) {
        final String sql = String.format(
            "UPDATE %s SET turns_taken = %d, team_queue = '%s' WHERE id = %d",
            TABLE_NAME, gameEntity.turns_taken(), gameEntity.team_queue(), id);

        return dbConnection.executeUpdate(sql).getFirst();
    }


    @Override
    public boolean deleteById(final long id) {
        final String sql = String.format("DELETE FROM %s WHERE id = %d", TABLE_NAME, id);
        return dbConnection.executeDelete(sql);
    }
}
