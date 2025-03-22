package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.spatial.Position;

public class Horse extends Piece {

    private static final String NAME = "h";

    public Horse(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Horse updatePosition(final Position position) {
        return new Horse(position, directions);
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    public String getName() {
        return NAME;
    }
}
