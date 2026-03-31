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
        if(!start.isHorizontal(start) && !start.isVertical(end)) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }

        Integer validDistance = calculateForwardMovement(side);
        if(start.isVertical(end) && start.calculateDistance(end) == validDistance) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }
        return super.findRoute(start, end);

    }

    private static Integer calculateForwardMovement(Side side) {
        if (side.equals(Side.CHO)) {
            return 1;
        }
        return -1;
    }
}
