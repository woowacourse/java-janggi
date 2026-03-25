package janggi.domain.piece;

import janggi.domain.BoardInterface;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.strategy.PoMoveStrategy;

public class Po extends Started {
    public Po(Side side) {
        super(new PoMoveStrategy(), side);
    }

    @Override
    public boolean isMovable(Position start, Position end, BoardInterface boardInterface) {
        return false;
    }

    @Override
    public boolean isPo(){
        return true;
    }
}
