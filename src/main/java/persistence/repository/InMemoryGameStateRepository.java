package persistence.repository;

import persistence.entity.GameState;

public final class InMemoryGameStateRepository implements GameStateRepository {

    private GameState gameState;

    @Override
    public void save(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public GameState load() {
        return gameState;
    }
}
