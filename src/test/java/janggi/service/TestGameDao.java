package janggi.service;

import janggi.dao.game.GameDao;
import janggi.dao.game.GameEntity;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestGameDao implements GameDao {

    private final Map<Long, String> turnByGameId = new HashMap<>();
    private long lastGameId = 0L;

    @Override
    public Long saveGame(Connection connection, String currentTurn) {
        lastGameId++;
        turnByGameId.put(lastGameId, currentTurn);
        return lastGameId;
    }

    @Override
    public List<GameEntity> findAllGames(Connection connection) {
        return turnByGameId.entrySet().stream()
                .map(entry -> new GameEntity(entry.getKey(), entry.getValue()))
                .toList();
    }


    @Override
    public GameEntity findGameByGameId(Connection connection, Long gameId) {
        if (!turnByGameId.containsKey(gameId)) {
            throw new IllegalArgumentException("해당 게임이 존재하지 않습니다.");
        }

        return new GameEntity(
                gameId,
                turnByGameId.get(gameId)
        );
    }

    @Override
    public void deleteGameByGameId(Connection connection, Long gameId) {
        if (!turnByGameId.containsKey(gameId)) {
            throw new IllegalStateException("해당 게임이 존재하지 않습니다.");
        }

        turnByGameId.remove(lastGameId);

        if (gameId.equals(lastGameId)) {
            lastGameId--;
        }
    }

    @Override
    public void updateGameOfCurrentTurn(
            Connection connection,
            Long gameId,
            String nextTurn
    ) {
        if (!turnByGameId.containsKey(gameId)) {
            throw new IllegalStateException("게임이 없습니다.");
        }

        turnByGameId.put(gameId, nextTurn);
    }
}
