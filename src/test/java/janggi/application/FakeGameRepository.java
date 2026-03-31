package janggi.application;

import janggi.application.dto.GameSnapshot;
import janggi.application.dto.GameSummary;
import java.util.List;
import java.util.Optional;

public class FakeGameRepository implements GameRepository {

    private final List<GameSummary> gameSummaries;
    private final GameSnapshot gameSnapshot;

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
    public void save(GameSnapshot gameSnapshot) {

    }

    @Override
    public void update(GameSnapshot gameSnapshot) {

    }
}
