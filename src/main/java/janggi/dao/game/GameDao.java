package janggi.dao.game;

import java.sql.Connection;
import java.util.List;

public interface GameDao {
    Long saveGame(Connection connection, String currentTurn);

    List<GameEntity> findAllGames(Connection connection);

    GameEntity findGameByGameId(Connection connection, Long gameId);

    void deleteGameByGameId(Connection connection, Long gameId);

    void updateGameOfCurrentTurn(Connection connection, Long gameId, String nextTurn);
}
