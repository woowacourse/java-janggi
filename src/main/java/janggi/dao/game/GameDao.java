package janggi.dao.game;

import java.sql.Connection;
import java.util.List;

public interface GameDao {
    Long saveGame(Connection con, String currentTurn);

    List<GameEntity> findAllGames(Connection con);

    GameEntity findGameByGameId(Connection con, Long gameId);

    void deleteGameByGameId(Connection con, Long gameId);

    void updateGameOfCurrentTurn(Connection con, Long gameId, String nextTurn);
}
