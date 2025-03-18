package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;

public class Rook extends Piece {

    public Rook(Side side, Position position) {
        super(side, position);
    }

    @Override
    protected boolean isMoveable(int x, int y) {
        if (getPosition().hasSameX(x)) {
            return !getPosition().hasSameY(y);
        }
        return getPosition().hasSameY(y);
    }
}
