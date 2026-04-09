package janggi.dao.game;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface GameDao {
    Long saveGame(Connection connection, String currentTurn);

    List<GameEntity> findAllGames(Connection connection);

    Optional<GameEntity> findGameByGameId(Connection connection, Long gameId);

    void deleteGameByGameId(Connection connection, Long gameId);

    void updateCurrentTurn(Connection connection, Long gameId, String nextTurn);
}
