package service;

import domain.board.Board;
import domain.game.JanggiGame;
import dto.GameSummary;
import dto.GameWrapper;
import java.util.List;
import repository.JanggiGameRepository;

public final class JanggiService {

    private final JanggiGameRepository repository;

    public JanggiService(JanggiGameRepository repository) {
        this.repository = repository;
    }

    public GameWrapper createGame(Board board) {
        JanggiGame janggiGame = new JanggiGame(board);
        long generatedKey = repository.save(janggiGame);

        return new GameWrapper(generatedKey, janggiGame);
    }

    public void saveGame(GameWrapper gameWrapper) {
        repository.updateGameStatus(gameWrapper.game(), gameWrapper.gameId());
    }

    public GameWrapper loadGame(long gameId) {
        JanggiGame loadedGame = repository.findById(gameId);

        return new GameWrapper(gameId, loadedGame);
    }

    public List<GameSummary> loadAllGameSummaries() {
        return repository.findAll();
    }

    void clear() {
        repository.clear();
    }
}
