package persistence;

import java.util.Optional;

public interface GameStateRepository {

    Optional<SavedGameState> load();

    void save(SaveGameStateRequest command);
}
