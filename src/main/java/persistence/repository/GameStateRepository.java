package persistence.repository;

import persistence.entity.GameState;

public interface GameStateRepository {

    void save(GameState gameState);

    GameState load();

    boolean exist();
}
