package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.List;

public class Cannon extends Piece {

    protected Cannon(Side side, Position position) {
        super(side, position);
    }

    @Override
    protected boolean isMoveablePosition(Position destination) {
        if (getPosition().hasSameX(destination)) {
            return !getPosition().hasSameY(destination);
        }
        return getPosition().hasSameY(destination);
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, Position destination) {
        return false;
    }
}
