package janggi.fixture;

import janggi.dao.GameDao;
import janggi.domain.game.Team;
import janggi.dto.GameDto;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FakeGameDao implements GameDao {

    private final Map<Integer, GameDto> games;

    public FakeGameDao(final Map<Integer, GameDto> games) {
        this.games = new HashMap<>(games);
    }


    @Override
    public List<GameDto> findAllGames(final Connection connection) {
        return games.values().stream()
                .toList();
    }

    @Override
    public GameDto findGameById(final Connection connection, final int gameId) {
        return games.values().stream()
                .filter(gameDto -> gameDto.id() == gameId)
                .findFirst()
                .orElseThrow();
    }

    @Override
    public int addGame(final Connection connection, final Team turn) {
        int gameId = games.size() + 1;
        games.put(gameId, new GameDto(gameId, turn.name(), LocalDateTime.now()));
        return gameId;
    }

    @Override
    public void updateGameById(final Connection connection, final int gameId, final Team turn) {
        games.put(gameId, new GameDto(gameId, turn.name(), games.get(gameId).createdAt()));
    }
    
    @Override
    public void deleteGameById(final Connection connection, final int id) {
        games.remove(id);
    }
}
