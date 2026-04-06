package janggi.dao.game;

import java.sql.Connection;
import java.util.Optional;

public interface GameDao {
    Long save(Connection con, String currentTurn);

    Optional<GameEntity> findLatestGame(Connection con);
    Optional<GameEntity> findByGameId(Connection con, Long gameId);

    void deleteByGameId(Connection con, Long gameId);

    void updateCurrentTurn(Connection con, Long gameId, String nextTurn);
}
