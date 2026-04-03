package janggi.repository;

import janggi.config.DBConnection;
import janggi.entity.GameStateEntity;
import janggi.global.EntityMapper;
import janggi.mapper.TurnManagerMapper;
import java.util.Optional;

public class GameStateRepositoryImpl implements GameStateRepository {

    private static final String TABLE_NAME = "game_states";

    private final DBConnection dbConnection;

    public GameStateRepositoryImpl(final DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public GameStateEntity save(final GameStateEntity gameStateEntity) {
        final String sql = String.format(
            "INSERT INTO %s (turns_taken, team_queue) VALUES (%d, '%s')",
            TABLE_NAME, gameStateEntity.turns_taken(), gameStateEntity.team_queue());
        final long generatedId = dbConnection.executeUpdate(sql);
        return new GameStateEntity(generatedId, gameStateEntity.turns_taken(), gameStateEntity.team_queue());
    }

    @Override
    public Optional<GameStateEntity> findById(final long targetId) {
        final String sql = String.format("SELECT id, turns_taken, team_queue FROM %s WHERE id = %d",
            TABLE_NAME, targetId);
        final EntityMapper<GameStateEntity> mapper = resultSet -> {
            long id = resultSet.getInt(1);
            int turnsTaken = resultSet.getInt(2);
            String teamQueue = resultSet.getString(3);
            return new GameStateEntity(id, turnsTaken, teamQueue);
        };
        return dbConnection.executeSelect(sql, mapper);
    }

    @Override
    public long update(final GameStateEntity gameStateEntity) {
        final String sql = String.format("UPDATE %s SET turns_taken = %d, team_queue = '%s' WHERE id = %d",
            TABLE_NAME, gameStateEntity.turns_taken(), gameStateEntity.team_queue(), gameStateEntity.id());

        return dbConnection.executeUpdate(sql);
    }


    @Override
    public boolean deleteById(final long id) {
        final String sql = String.format("DELETE FROM %s WHERE id = %d", TABLE_NAME, id);
        return dbConnection.executeDelete(sql);
    }
}
