package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.Position;

public class King extends Piece {

    private static final String NAME = "K";

    public King(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public King updatePosition(final Position position) {
        return new King(position, directions);
    }

    @Override
    public boolean isKing() {
        return true;
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    public String getName() {
        return NAME;
    }
}
