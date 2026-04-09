package janggi.repository;

import janggi.domain.JanggiGame;
import janggi.dto.GameInfo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FakeGameRepository implements GameRepository {

    private final Map<Long, JanggiGame> games = new LinkedHashMap<>();
    private long id;

    @Override
    public long createGame(JanggiGame game) {
        games.put(++id, game);
        return id;
    }

    @Override
    public void updateGame(long gameId, JanggiGame game) {
        if (!games.containsKey(gameId)) {
            throw new IllegalArgumentException("[ERROR] 해당 게임이 존재하지 않습니다.");
        }
        games.put(gameId, game);
    }

    @Override
    public JanggiGame getById(long gameId) {
        if (!games.containsKey(gameId)) {
            throw new IllegalArgumentException("[ERROR] 해당 게임이 존재하지 않습니다.");
        }
        return games.get(gameId);
    }

    @Override
    public void deleteGame(long gameId) {
        if (games.remove(gameId) == null) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 게임 ID입니다.");
        }
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
