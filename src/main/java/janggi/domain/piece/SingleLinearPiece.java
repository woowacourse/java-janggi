package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;

public class SingleLinearPiece extends LinearPiece {
    private static final int MAX_DISTANCE = 1;

    public SingleLinearPiece(Side side, PieceType pieceType) {
        super(new ClearPathPolicy(), side, pieceType);
    }

    @Override
    public Route findRoute(Position start, Position end) {
        if(Math.abs(start.calculateRowDistance(end) + start.calculateColumnDistance(end)) != MAX_DISTANCE) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }
        return super.findRoute(start, end);
    }
}
