package janggi.service.dto;

import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.Piece;
import janggi.domain.state.GameContext;

public class GameInformation {

    private final Long gameId;
    private final Board board;
    private final GameContext gameContext;

    private GameInformation(Long gameId, Board board, GameContext gameContext) {
        this.gameId = gameId;
        this.board = board;
        this.gameContext = gameContext;
    }

    public static GameInformation of(Long gameId, Board board, Side currentSide) {
        GameContext gameContext = GameContext.createInProgress(board.getAlivePieces(), currentSide);
        return new GameInformation(gameId, board, gameContext);
    }

    public Long getGameId() {
        return gameId;
    }

    public Board getBoard() {
        return board;
    }

    public Side getCurrentSide() {
        return gameContext.getCurrentSide();
    }

    public boolean isInProgress() {
        return gameContext.isInProgress();
    }

    public Side getWinner() {
        return gameContext.getWinner();
    }

    public void update(Piece removedPiece) {
        gameContext.update(removedPiece);
    }
}
