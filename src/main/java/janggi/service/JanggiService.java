package janggi.service;

import janggi.db.repository.GameRepository;
import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.board.strategy.ArrangementStrategy;
import janggi.domain.board.strategy.BoardAssembler;
import janggi.dto.GameDto;
import java.util.List;

public class JanggiService {

    private static final int PLAYABLE_GAME_AMOUNT = 1;

    private final GameRepository gameRepository;

    public JanggiService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public boolean checkIfAnyOngoingGameExists() {
        int existingGameCount = gameRepository.countOngoingGame();
        return existingGameCount >= PLAYABLE_GAME_AMOUNT;
    }

    public GameDto loadOngoingGame() {
        return gameRepository.loadOngoingGame();
    }

    public GameDto startNewGame(ArrangementStrategy hanStrategy, ArrangementStrategy choStrategy) {
        Board board = Board.create(BoardAssembler.from(List.of(hanStrategy, choStrategy)));
        Side currentTurn = Side.CHO;
        int gameId = gameRepository.saveNewGame(board, currentTurn);
        return new GameDto(gameId, board, currentTurn);
    }

    public void saveGame(int gameId, Board board, Side currentTurn) {
        gameRepository.saveGame(gameId, board, currentTurn);
    }

    public void finishGame(int gameId) {
        gameRepository.deleteGame(gameId);
    }
}
