package application;

import domain.board.SangSetup;
import domain.game.GameScore;
import domain.position.Position;
import java.util.function.Supplier;

public class GameService {

    private final GamePersistenceService persistenceService;

    public GameService(GamePersistenceService persistenceService) {
        this.persistenceService = persistenceService;
    }

    public GameStartResult startOrResume(Supplier<GameSession> newGameSessionSupplier) {
        return persistenceService.loadLatestRunningGame()
                .map(GameStartResult::resumed)
                .orElseGet(() -> GameStartResult.started(newGameSessionSupplier.get()));
    }

    public GameSession createNewGame(SangSetup choSangSetup, SangSetup hanSangSetup) {
        return persistenceService.createNewGame(choSangSetup, hanSangSetup);
    }

    public void move(GameSession session, Position departure, Position destination) {
        session.game().move(departure, destination);
        persistenceService.saveProgress(session);
    }

    public GameScore finishByScore(GameSession session) {
        GameScore gameScore = session.game().finishByScore();
        persistenceService.saveProgress(session);
        return gameScore;
    }
}
