package janggi.service;

import janggi.dao.game.GameDao;
import janggi.dao.game.GameEntity;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TestGameDao implements GameDao {

   private final Map<Long, String> turnByGameId = new HashMap<>();
   private long lastGameId = 0L;

    @Override
    public Long save(Connection con, String currentTurn) {
        lastGameId++;
        turnByGameId.put(lastGameId, currentTurn);
        return lastGameId;
    }

    @Override
    public Optional<GameEntity> findLatestGame(Connection con) {
        if (turnByGameId.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new GameEntity(
                lastGameId,
                turnByGameId.get(lastGameId))
        );
    }

    @Override
    public Optional<GameEntity> findByGameId(Connection con, Long gameId) {
        if (!turnByGameId.containsKey(gameId)) {
            return Optional.empty();
        }

        return Optional.of(new GameEntity(
                gameId,
                turnByGameId.get(gameId))
        );
    }

    @Override
    public void deleteByGameId(Connection con, Long gameId) {
        if (!turnByGameId.containsKey(gameId)) {
            throw new IllegalStateException("게임이 없습니다.");
        }

        turnByGameId.remove(lastGameId);

        if (gameId.equals(lastGameId)) {
            lastGameId--;
        }
    }

    @Override
    public void updateCurrentTurn(
            Connection con,
            Long gameId,
            String nextTurn
    ) {
        if (!turnByGameId.containsKey(gameId)) {
            throw new IllegalStateException("게임이 없습니다.");
        }

        turnByGameId.put(gameId, nextTurn);
    }
}
