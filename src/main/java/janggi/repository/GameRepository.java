package janggi.repository;

import janggi.entity.GameEntity;

import java.sql.Connection;

public interface GameRepository {
    void save(Connection conn, GameEntity gameEntity);

    void updateTurn(Connection conn, int gameId, String nextTurn);
}
