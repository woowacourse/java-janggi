package service;

import domain.board.Board;
import domain.game.JanggiGame;
import dto.GameSummary;
import dto.GameWrapper;
import java.util.List;
import repository.JanggiGameRepository;
import support.TransactionTemplate;

public final class JanggiService {

    private final TransactionTemplate transactionTemplate;
    private final JanggiGameRepository repository;

    public JanggiService(TransactionTemplate transactionTemplate, JanggiGameRepository repository) {
        this.transactionTemplate = transactionTemplate;
        this.repository = repository;
    }

    public GameWrapper createGame(Board board) {
        return transactionTemplate.execute(conn -> {
            JanggiGame janggiGame = new JanggiGame(board);
            long generatedKey = repository.save(conn, janggiGame);
            return new GameWrapper(generatedKey, janggiGame);
        });
    }

    public void saveGame(GameWrapper gameWrapper) {
        transactionTemplate.execute(conn -> {
            repository.updateGameStatus(conn, gameWrapper.game(), gameWrapper.gameId());
            return null;
        });
    }

    public GameWrapper loadGame(long gameId) {
        return transactionTemplate.execute(conn -> {
            JanggiGame loadedGame = repository.findById(conn, gameId);
            return new GameWrapper(gameId, loadedGame);
        });
    }

    public List<GameSummary> loadAllGameSummaries() {
        return transactionTemplate.execute(repository::findAll);
    }

    void clear() {
        repository.clear();
    }
}
