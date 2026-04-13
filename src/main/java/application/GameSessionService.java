package application;

import application.port.GameSessionRepository;
import application.port.StoredGameSession;
import domain.game.JanggiGame;
import domain.setup.Command;

public class GameSessionService {
    private final GameSessionRepository gameSessionRepository;
    private final GameReplayer gameReplayer;

    public GameSessionService(GameSessionRepository gameSessionRepository, GameReplayer gameReplayer) {
        this.gameSessionRepository = gameSessionRepository;
        this.gameReplayer = gameReplayer;
    }

    public GameSession loadOrStart() {
        return gameSessionRepository.findInProgress()
                .map(this::restore)
                .orElseGet(this::startNewSession);
    }

    private GameSession restore(StoredGameSession storedGameSession) {
        JanggiGame game = gameReplayer.replay(storedGameSession.rawCommands());
        return new GameSession(storedGameSession.id(), game);
    }

    private GameSession startNewSession() {
        long id = gameSessionRepository.create();
        return new GameSession(id, new JanggiGame());
    }

    public void execute(GameSession gameSession, String rawCommand) {
        gameSession.game().processCommand(new Command(rawCommand));
        gameSessionRepository.appendCommand(gameSession.id(), rawCommand);
        finish(gameSession);
    }

    private void finish(GameSession gameSession) {
        if (!gameSession.game().isFinishPhase()) {
            return;
        }
        gameSessionRepository.finish(gameSession.id());
    }
}
