package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;
import janggi.domain.board.Palace;

public class Sa extends SingleLinearPiece {
    public Sa(Side side) {
        super( side, PieceType.SA);
    }

    @Override
    public Route findRoute(Position start, Position end) {
        if(!Palace.isInside(start) || !Palace.isInside(end)) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }

        return super.findRoute(start, end);
    }
}
