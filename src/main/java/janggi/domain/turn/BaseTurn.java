package janggi.domain.turn;

import janggi.domain.Side;
import janggi.domain.SideScore;
import janggi.domain.board.Board;
import janggi.domain.piece.PieceAttribute;
import java.util.List;

public abstract class BaseTurn implements PlayerTurn {
    protected static final int MAX_TURN = 200;

    protected final Board board;

    public BaseTurn(Board board) {
        this.board = board;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public List<List<PieceAttribute>> getCurrentBoard() {
        return board.getCurrentBoard();
    }

    @Override
    public Side getWinnerSide() {
        return board.getHighestScoreSide();
    }

    @Override
    public SideScore getCurrentScore() {
        return board.getScore();
    }
}
