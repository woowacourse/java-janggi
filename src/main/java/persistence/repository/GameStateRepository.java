package persistence.repository;

import java.util.Optional;
import persistence.entity.GameState;

public interface GameStateRepository {

    void save(GameState gameState);

    Optional<GameState> load();

    boolean exist();
}
