package janggi.application;

import janggi.application.dto.GameSnapshot;
import janggi.application.dto.GameSummary;
import java.util.List;
import java.util.Optional;

public class FakeGameRepository implements GameRepository {

    private final List<GameSummary> gameSummaries;

    public FakeGameRepository(List<GameSummary> gameSummaries) {
        this.gameSummaries = gameSummaries;
    }

    @Override
    public List<GameSummary> findAll() {
        return gameSummaries;
    }

    @Override
    public Optional<GameSnapshot> findById(Long gameId) {
        return Optional.empty();
    }

    @Override
    public void save(GameSnapshot gameSnapshot) {

    }

    @Override
    public void update(GameSnapshot gameSnapshot) {

    }
}
