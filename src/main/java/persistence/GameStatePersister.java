package persistence;

import java.util.Optional;

import domain.GameDeadline;
import domain.GameStatus;
import domain.JanggiGame;
import domain.TeamColor;

public class GameStatePersister {

    private final GameStateRepository repository;

    public GameStatePersister(GameStateRepository repository) {
        this.repository = repository;
    }

    public Optional<SavedGameState> load() {
        return repository.load();
    }

    public void saveInProgress(JanggiGame game, GameDeadline deadline) {
        repository.save(new SaveGameStateRequest(
                game.captureSnapshot(),
                game.currentTurn(),
                GameStatus.IN_PROGRESS,
                null,
                deadline));
    }

    public void saveEnded(JanggiGame game, TeamColor winner, GameDeadline deadline) {
        repository.save(new SaveGameStateRequest(
                game.captureSnapshot(),
                game.currentTurn(),
                GameStatus.ENDED,
                winner,
                deadline));
    }

    public void saveWithDeadline(SavedGameState saved, GameDeadline deadline) {
        repository.save(new SaveGameStateRequest(
                saved.snapshot(),
                saved.currentTurn(),
                saved.gameStatus(),
                saved.winner(),
                deadline));
    }
}
