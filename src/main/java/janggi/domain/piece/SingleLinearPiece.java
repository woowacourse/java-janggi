package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;

public class SingleLinearPiece extends LinearPiece {
    public SingleLinearPiece(Side side, PieceType pieceType) {
        super(new ClearPathPolicy(), side, pieceType);
    }

    @Override
    public Route findRoute(Position start, Position end) {
        if(!start.isVertical(end) && !start.isHorizontal(end)) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }

        if(Math.abs(start.calculateDistance(end)) != 1) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }

        return super.findRoute(start, end);
    }
}
