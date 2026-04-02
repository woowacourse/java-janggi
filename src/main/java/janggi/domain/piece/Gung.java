package janggi.domain.piece;

import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Side;

public class Gung extends SingleLinearPiece {
    public Gung(Side side) {
        super(side, PieceType.GUNG);
    }

    @Override
    public Route findRoute(Position start, Position end) {
        if(!isGungSung(end)) {
            throw new IllegalArgumentException(INVALID_DESTINATION_MESSAGE);
        }

        return super.findRoute(start, end);
    }
}
