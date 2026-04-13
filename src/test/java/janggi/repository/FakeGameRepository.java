package janggi.repository;

import janggi.domain.JanggiGame;
import janggi.dto.GameInfo;

import java.util.*;

public class FakeGameRepository implements GameRepository {

    private final Map<Long, JanggiGame> games = new LinkedHashMap<>();
    private long id;

    @Override
    public long createGame(JanggiGame game) {
        games.put(++id, game);
        return id;
    }

    @Override
    public void saveGameState(long gameId, JanggiGame game) {
        games.put(gameId, game);
    }

    @Override
    public Optional<JanggiGame> getById(long gameId) {
        if (!games.containsKey(gameId)) {
            return Optional.empty();
        }
        return Optional.of(games.get(gameId));
    }

    @Override
    public boolean deleteGame(long gameId) {
        return games.remove(gameId) != null;
    }

    @Override
    public List<GameInfo> findAllGames() {
        List<GameInfo> result = new ArrayList<>();
        for (Map.Entry<Long, JanggiGame> entry : games.entrySet()) {
            JanggiGame game = entry.getValue();
            result.add(new GameInfo(
                    entry.getKey(),
                    java.time.LocalDateTime.now(),
                    game.getScore().getHanScore(),
                    game.getScore().getChoScore(),
                    game.getCurrentTeam(),
                    game.getWinner()
            ));
        }
        return result;
    }
}
