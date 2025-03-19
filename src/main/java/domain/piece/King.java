package domain.piece;

import domain.direction.Directions;

public class King extends Piece {

    private static final String NAME = "K";

    public King(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

    public String getName() {
        return NAME;
    }
}
