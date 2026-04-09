package janggi.service;

import janggi.db.TransactionManager;
import janggi.domain.Game;
import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.repository.GameRepository;
import java.util.List;

public class GameService {

    private static final String INVALID_GAME_ROOM = "[ERROR] 존재하지 않는 게임방 번호입니다.";

    private final TransactionManager transactionManager;
    private final GameRepository gameRepository;

    public GameService(TransactionManager transactionManager, GameRepository gameRepository) {
        this.transactionManager = transactionManager;
        this.gameRepository = gameRepository;
    }

    public boolean playEachTurn(LoadedGame loadedGame, Position source, Position destination) {
        long id = loadedGame.id();
        Game game = loadedGame.game();

        return transactionManager.executeWithTransaction(connection -> {
            boolean gameEnded = game.play(source, destination);
            if (gameEnded) {
                gameRepository.deleteById(connection, id);
                return false;
            }
            gameRepository.update(connection, id, game);
            return true;
        });
    }

    public LoadedGame loadGame(long gameId) {
        Game game = transactionManager.execute(connection ->
                gameRepository.findById(connection, gameId)
                        .orElseThrow(() -> new IllegalArgumentException(INVALID_GAME_ROOM))
        );
        return new LoadedGame(gameId, game);
    }

    public long createNewGame(Board board) {
        Game game = Game.newGame(board);
        return transactionManager.executeWithTransaction(connection -> {
            return gameRepository.create(connection, game);
        });
        return new LoadedGame(gameId, game);
    }

    public List<Long> getAllIds() {
        return transactionManager.execute(gameRepository::findAllIds);
    }
}
