package janggi.service;

import janggi.db.TransactionManager;
import janggi.domain.Game;
import janggi.domain.board.Board;
import janggi.repository.GameRepository;
import java.util.List;
import java.util.Optional;

public class GameService {

    private final TransactionManager transactionManager;
    private final GameRepository gameRepository;

    public GameService(TransactionManager transactionManager, GameRepository gameRepository) {
        this.transactionManager = transactionManager;
        this.gameRepository = gameRepository;
    }

    public List<Long> findAllIds() {
        return transactionManager.withoutTransaction(gameRepository::findAllIds);
    }

    public Optional<LoadedGame> findById(long gameId) {
        Optional<Game> foundGame = transactionManager.withoutTransaction(connection ->
                gameRepository.findById(connection, gameId)
        );
        return foundGame.map(game -> new LoadedGame(gameId, game));
    }

    public LoadedGame create(Board board) {
        Game game = Game.start(board);
        long gameId = transactionManager.inTransaction(connection -> {
            return gameRepository.create(connection, game);
        });
        return new LoadedGame(gameId, game);
    }

    public void update(long id, Game game) {
        transactionManager.inTransaction(connection -> {
            gameRepository.update(connection, id, game);
        });
    }

    public void deleteById(long gameId) {
        transactionManager.inTransaction(connection -> {
            gameRepository.deleteById(connection, gameId);
        });
    }
}
