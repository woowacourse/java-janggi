package janggi.domain.turn;

import janggi.domain.PieceInfo;
import janggi.domain.ScoreStatus;
import janggi.domain.Side;
import janggi.domain.board.Board;

public abstract class Started implements PlayerTurn {
    protected final Board board;
    protected final Side side;

    public Started(Board board, Side side) {
        this.board = board;
        this.side = side;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public PieceInfo[][] getCurrentBoard() {
        return board.getCurrentBoard();
    }

    @Override
    public Side getCurrentSide() {
        return side;
    }

    @Override
    public ScoreStatus getCurrentScoreStatus(){
        return board.getScoreStatus();
    }
}
