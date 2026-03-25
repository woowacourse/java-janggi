package janggi.domain.piece;

import janggi.domain.BoardInterface;
import janggi.domain.Position;
import janggi.domain.Side;
import janggi.domain.strategy.DefaultMoveStrategy;

public class Sang extends Started {
    public Sang(Side side) {
        super(new DefaultMoveStrategy(), side);
    }

    @Override
    public boolean isMovable(Position start, Position end, BoardInterface boardInterface) {
        return false;
    }
}
