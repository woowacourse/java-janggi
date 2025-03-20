package domain.piece;

import domain.direction.Directions;

public class King extends Piece {

    private static final String NAME = "K";

    public King(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

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

    public String getName() {
        return NAME;
    }
}
