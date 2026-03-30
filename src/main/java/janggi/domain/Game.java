package janggi.domain;

import janggi.domain.board.Board;
import janggi.domain.turn.ChoTurn;
import janggi.domain.turn.PlayerTurn;
import janggi.initializer.BoardInitializer;

public class Game {
    private PlayerTurn playerTurn;

    public Game(Arrangement choArrangement, Arrangement hanArrangement) {
        this.playerTurn = new ChoTurn(new Board(BoardInitializer.createBoard(choArrangement, hanArrangement)));
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
}
