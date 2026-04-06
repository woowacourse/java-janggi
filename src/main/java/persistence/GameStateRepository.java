package persistence;

import domain.GameSnapshot;
import domain.GameStatus;
import domain.GameDeadline;
import domain.TeamColor;
import java.util.Optional;

public interface GameStateRepository {

    Optional<SavedGameState> load();

    void save(
            GameSnapshot snapshot,
            TeamColor currentTurn,
            GameStatus gameStatus,
            Optional<TeamColor> winner,
            Optional<GameDeadline> deadline);
}
