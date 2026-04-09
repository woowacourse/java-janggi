package janggi.service;

import java.util.Optional;

import janggi.domain.board.coordinate.Point;
import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.game.Game;
import janggi.domain.game.GameResult;
import janggi.domain.game.GameSession;
import janggi.domain.side.Side;
import janggi.repository.GameRepository;
import janggi.repository.TransactionManager;

public class GameService {

    private final TransactionManager transactionManager;
    private final GameRepository gameRepository;

    public GameService(TransactionManager transactionManager, GameRepository gameRepository) {
        this.transactionManager = transactionManager;
        this.gameRepository = gameRepository;
    }

    public Optional<Integer> findActiveGameId() {
        return transactionManager.execute(() -> gameRepository.findActiveGameId());
    }

    public GameSession continueGame(int gameId) {
        Game game = transactionManager.execute(() -> gameRepository.load(gameId));
        return new GameSession(game, gameId);
    }

    public GameSession createGame(BoardSetUp choBoardSetUp, BoardSetUp hanBoardSetUp) {
        Game game = Game.createGame(choBoardSetUp, hanBoardSetUp);
        int gameId = transactionManager.execute(() -> gameRepository.save(game));
        return new GameSession(game, gameId);
    }

    public GameResult move(GameSession session, Point from, Point to) {
        GameResult result = session.game().move(from, to);
        transactionManager.execute(() -> {
            gameRepository.update(session.gameId(), session.game());
            if (result.isGameOver()) {
                gameRepository.finish(session.gameId(), result.getWinner());
            }
        });
        return result;
    }

    public void finish(int gameId, Side winner) {
        transactionManager.execute(() -> gameRepository.finish(gameId, winner));
    }
}
