package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.turn.ChoTurn;
import janggi.domain.turn.PlayerTurn;
import janggi.factory.BoardFactory;

public class Game {
    private PlayerTurn playerTurn;

    public Game(Arrangement choArrangement, Arrangement hanArrangement) {
        this.playerTurn = new ChoTurn(new Board(
                BoardFactory.createInitialBoard(choArrangement, hanArrangement),
                BoardFactory.createInitialScoresBySide()));
    }

    public Game(PlayerTurn playerTurn) {
        this.playerTurn = playerTurn;
    }

    public void move(Position start, Position end) {
        playerTurn = playerTurn.move(start, end);
    }

    public boolean isFinished() {
        return playerTurn.isFinished();
    }

    public PieceInfo[][] getCurrentBoard() {
        return playerTurn.getCurrentBoard();
    }

    public Side getCurrentSide() {
        return playerTurn.getCurrentSide();
    }

    public ScoreStatus getCurrentScoreStatus() {
        return playerTurn.getCurrentScoreStatus();
    }
}
