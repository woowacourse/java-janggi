package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Side;
import java.util.List;

public class Guard extends Piece {

    protected Guard(Side side, int x, int y) {
        super(side, x, y);
    }

    @Override
    protected boolean isMoveablePosition(Position destination) {
        return false;
    }

    @Override
    protected boolean isMoveablePath(List<Piece> existingPieces, Position destination) {
        return false;
    }
}
