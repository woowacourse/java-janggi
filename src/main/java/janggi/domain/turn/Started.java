package janggi.domain.turn;

import janggi.domain.PieceInfo;
import janggi.domain.Side;
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

    @Override
    public PieceInfo[][] getCurrentBoard() {
        return board.getCurrentBoard();
    }

    @Override
    public Side getWinnerSide(){
        throw new IllegalStateException("게임이 아직 끝나지 않았습니다.");
    }
}
