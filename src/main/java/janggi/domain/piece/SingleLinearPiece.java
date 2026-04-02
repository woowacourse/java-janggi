package janggi.domain.piece;

import janggi.domain.Movement;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;

public class SingleLinearPiece extends LinearPiece {
    private static final int RESTRICTED_DISTANCE = 1;

    public SingleLinearPiece(Side side, PieceType pieceType) {
        super(new ClearPathPolicy(), side, pieceType);
    }

    @Override
    public Route findRoute(Position start, Position end) {
        try {
            int distance = calculateLinearDistance(start, end);
            validateDistance(distance);

            return super.findRoute(start, end);
        } catch (IllegalStateException e) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }
    }

    private void validateDistance(int distance) {
        if(distance != RESTRICTED_DISTANCE) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }
    }
}
