package janggi.repository;

import janggi.entity.GameEntity;

import java.sql.Connection;
import java.util.Optional;

public interface GameRepository {
    void save(Connection conn, GameEntity gameEntity);

    void updateTurn(Connection conn, int gameId, String nextTurn);

    Optional<GameEntity> findById(Connection conn, int gameId);
}
