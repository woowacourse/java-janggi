package janggi.service;

import janggi.db.TransactionManager;
import janggi.domain.Game;
import janggi.domain.board.Board;
import janggi.domain.board.Position;
import janggi.repository.GameRepository;
import java.sql.Connection;
import java.util.List;

public class GameService {

    private static final String INVALID_GAME_ROOM = "[ERROR] 존재하지 않는 게임방 번호입니다.";

    private final TransactionManager transactionManager;
    private final GameRepository gameRepository;

    public GameService(TransactionManager transactionManager, GameRepository gameRepository) {
        this.transactionManager = transactionManager;
        this.gameRepository = gameRepository;
    }

    public GameStatus playEachTurn(long gameId, Position source, Position destination) {
        return transactionManager.executeWithTransaction(connection -> {
            Game game = findGame(connection, gameId);

            boolean gameEnded = game.play(source, destination);
            GameStatus gameStatus = GameStatus.from(game, gameEnded);
            if (gameEnded) {
                gameRepository.deleteById(connection, gameId);
                return gameStatus;
            }
            gameRepository.update(connection, gameId, game);
            return gameStatus;
        });
    }

    public void validateGameExists(long gameId) {
        transactionManager.execute(connection -> {
            findGame(connection, gameId);
        });
    }

    public long createNewGame(Board board) {
        Game game = Game.newGame(board);
        return transactionManager.executeWithTransaction(connection -> {
            return gameRepository.create(connection, game);
        });
    }

    public List<Long> getAllIds() {
        return transactionManager.execute(gameRepository::findAllIds);
    }

    public GameStatus getGameStatus(long gameId) {
        return transactionManager.execute(connection -> {
            return GameStatus.from(findGame(connection, gameId), false);
        });
    }

    public void validateSourceForCurrentTurn(long gameId, Position source) {
        transactionManager.execute(connection -> {
            findGame(connection, gameId).validateSourceForCurrentTurn(source);
        });
    }

    private Game findGame(Connection connection, long gameId) {
        return gameRepository.findById(connection, gameId)
                .orElseThrow(() -> new IllegalArgumentException(INVALID_GAME_ROOM));
    }
}
