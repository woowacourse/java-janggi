package janggi.dao.game;

import java.sql.Connection;
import java.util.Optional;

public interface GameDao {
    Long save(Connection con, String currentTurn);

    Optional<GameEntity> findLatestGame(Connection con);

    void deleteByGameId(Connection con, Long gameId);
}
