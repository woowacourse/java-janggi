package application;

import domain.board.SangSetup;
import domain.game.GameScore;
import domain.game.JanggiGame;
import domain.position.Position;
import java.util.function.Supplier;

public class GameService {

    private final GamePersistenceService persistenceService;

    public GameService(GamePersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public GameStartResult startOrResume(Supplier<JanggiGame> newGameSupplier) {
        return persistenceService.loadLatestRunningGame()
                .map(GameStartResult::resumed)
                .orElseGet(() -> GameStartResult.started(newGameSupplier.get()));
    }

    public JanggiGame createNewGame(SangSetup choSangSetup, SangSetup hanSangSetup) {
        return persistenceService.createNewGame(choSangSetup, hanSangSetup);
    }

    public void move(JanggiGame janggiGame, Position departure, Position destination) {
        janggiGame.move(departure, destination);
        persistenceService.saveProgress(janggiGame);
    }

    public GameScore finishByScore(JanggiGame janggiGame) {
        GameScore gameScore = janggiGame.finishByScore();
        persistenceService.saveProgress(janggiGame);
        return gameScore;
    }
}
