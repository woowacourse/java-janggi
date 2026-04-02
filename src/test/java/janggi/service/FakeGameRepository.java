package janggi.service;

import janggi.dto.GameSnapshot;
import janggi.dto.GameSummary;
import java.util.List;
import java.util.Optional;

public class FakeGameRepository implements GameRepository {

    private final List<GameSummary> gameSummaries;
    private final GameSnapshot gameSnapshot;
    private GameSnapshot updatedGameSnapshot;
    private GameSnapshot savedGameSnapshot;

    public FakeGameRepository(List<GameSummary> gameSummaries, GameSnapshot gameSnapshot) {
        this.gameSummaries = gameSummaries;
        this.gameSnapshot = gameSnapshot;
    }

    @Override
    public List<GameSummary> findAll() {
        return gameSummaries;
    }

    @Override
    public Optional<GameSnapshot> findById(Long gameId) {
        if (gameSnapshot == null) {
            return Optional.empty();
        }
        if (!gameSnapshot.id().equals(gameId)) {
            return Optional.empty();
        }
        return Optional.of(gameSnapshot);
    }

    @Override
    public Long save(GameSnapshot gameSnapshot) {
        this.savedGameSnapshot = gameSnapshot;
        return 1L;
    }

    @Override
    public void update(GameSnapshot gameSnapshot) {
        this.updatedGameSnapshot = gameSnapshot;
    }

    public GameSnapshot updatedGameSnapshot() {
        return updatedGameSnapshot;
    }

    public GameSnapshot savedGameSnapshot() {
        return savedGameSnapshot;
    }
}
