package janggi.domain.turn;

import janggi.domain.board.Board;

public abstract class Started implements PlayerTurn {
    protected final Board board;

    public Started(Board board) {
        this.board = board;
    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
