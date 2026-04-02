package janggi.repository;

import janggi.config.DBConnection;
import janggi.entity.GameStateEntity;
import janggi.global.EntityMapper;

public class GameStateRepositoryImpl implements GameStateRepository {

    private static final String TABLE_NAME = "game_states";

    private final DBConnection dbConnection;

    public GameStateRepositoryImpl(final DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public long save(final GameStateEntity gameStateEntity) {
        final String sql = String.format(
            "INSERT INTO %s (turns_taken, team_queue) VALUES (%d, '%s')",
            TABLE_NAME, gameStateEntity.turns_taken(), gameStateEntity.team_queue());
        return dbConnection.executeUpdate(sql);
    }

    @Override
    public GameStateEntity findById(final long targetId) {
        final String sql = String.format("SELECT id, turns_taken, team_queue FROM %s WHERE id = %d",
            TABLE_NAME, targetId);
        final EntityMapper<GameStateEntity> mapper = resultSet -> {
            int id = 0;
            int turnsTaken = 0;
            String teamQueue = "";
            while (resultSet.next()) {
                id = resultSet.getInt(1);
                turnsTaken = resultSet.getInt(2);
                teamQueue = resultSet.getString(3);
            }
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
