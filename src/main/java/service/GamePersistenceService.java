package service;

import domain.game.JanggiGame;
import java.util.List;
import java.util.function.Supplier;
import repository.GameRepository;

public class GamePersistenceService {

    private static final String ERROR_GAME_NOT_FOUND = "게임을 찾을 수 없습니다.";

    private final GameRepository gameRepository;

    public GamePersistenceService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public long loadOrCreateGameId(Supplier<JanggiGame> newGameSupplier) {
        return gameRepository.findInProgressGameId()
                .orElseGet(() -> gameRepository.save(newGameSupplier.get()));
    }

    public JanggiGame loadGame(long gameId) {
        return gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalStateException(ERROR_GAME_NOT_FOUND));
    }

    public void playTurnAndSave(long gameId, JanggiGame janggiGame, List<Integer> from, List<Integer> to) {
        janggiGame.playTurn(from, to);
        gameRepository.save(gameId, janggiGame);
    }
}
