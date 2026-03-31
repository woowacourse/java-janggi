package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;

public class Pawn extends SingleLinearPiece {
    public Pawn(Side side) {
        super(side, PieceType.PAWN);
    }


    @Override
    public Route findRoute(Position start, Position end) {
        if(isBackward(start, end, side)) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }
        return super.findRoute(start, end);

    }

    private boolean isBackward(Position start, Position end, Side side) {
        if (side.equals(Side.CHO)) {
            return start.calculateRowDistance(end) > 0;
        }
        return start.calculateRowDistance(end) < 0;
    }
}
