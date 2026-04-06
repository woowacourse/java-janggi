package persistence;

import domain.GameSnapshot;
import domain.TeamColor;
import java.util.Optional;

public interface GameStateRepository {

    Optional<SavedGameState> load();

    void save(GameSnapshot snapshot, TeamColor currentTurn);
}
