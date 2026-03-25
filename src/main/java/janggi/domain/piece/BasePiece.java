package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.strategy.MoveStrategy;

public abstract class BasePiece implements Piece {
    protected MoveStrategy moveStrategy;
    protected Side side;

    public BasePiece(MoveStrategy moveStrategy, Side side) {
        this.moveStrategy = moveStrategy;
        this.side = side;
    }

    @Override
    public boolean isPo() {
        return false;
    }
}
