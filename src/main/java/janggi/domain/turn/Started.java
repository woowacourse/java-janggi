package janggi.domain.turn;

import janggi.domain.PieceInfo;
import janggi.domain.Side;
import janggi.domain.board.Board;

public abstract class Started implements PlayerTurn {
    protected final Board board;
    protected final Side winnerSide;

    public Started(Board board, Side winnerSide) {
        this.board = board;
        this.winnerSide = winnerSide;
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
