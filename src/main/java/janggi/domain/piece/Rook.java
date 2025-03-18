package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;

import java.util.List;

public class Rook extends Piece {

    public Rook(Side side, Position position) {
        super(side, position);
    }

    @Override
    protected boolean isMoveablePosition(int x, int y) {
        if (getPosition().hasSameX(x)) {
            return !getPosition().hasSameY(y);
        }
        return getPosition().hasSameY(y);
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, int x, int y) {
        return false;
    }
}
