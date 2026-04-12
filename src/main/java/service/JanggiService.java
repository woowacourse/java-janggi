package service;

import domain.board.Board;
import domain.board.Intersection;
import domain.game.JanggiGame;
import domain.game.Side;
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
            JanggiGame janggiGame = JanggiGame.create(board);
            long generatedKey = repository.save(conn, janggiGame);
            return new GameWrapper(generatedKey, janggiGame);
        });
    }

    public GameWrapper move(long gameId, Intersection from, Intersection to, Side requestingSide) {
        return transactionTemplate.execute(conn -> {
            JanggiGame janggiGame = repository.findById(conn, gameId);

            janggiGame.movePiece(from, to, requestingSide);

            repository.updateGameStatus(conn, janggiGame, from, to, gameId);
            return new GameWrapper(gameId, janggiGame);
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
}
