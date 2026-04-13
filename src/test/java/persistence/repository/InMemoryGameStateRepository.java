package persistence.repository;

import java.util.Optional;
import persistence.entity.GameState;

public final class InMemoryGameStateRepository implements GameStateRepository {

    private GameState gameState;

    @Override
    public void save(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public Optional<GameState> load() {
        return Optional.ofNullable(gameState);
    }

    @Override
    public boolean exist() {
        return false;
    }
}
