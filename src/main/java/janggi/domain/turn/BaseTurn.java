package janggi.domain.turn;

import janggi.domain.Side;
import janggi.domain.board.Board;
import janggi.domain.piece.PieceManifest;
import java.util.List;

public abstract class BaseTurn implements PlayerTurn {
    protected final Board board;
    protected final Side side;

    public BaseTurn(Board board, Side side) {
        this.board = board;
        this.side = side;
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public List<List<PieceManifest>> getCurrentBoard() {
        return board.getCurrentBoard();
    }

    @Override
    public Side getCurrentSide() {
        return side;
    }

    @Override
    public Side getWinnerSide() {
        return Side.EMPTY;
    }
}
