package domain.piece;

import domain.direction.Directions;

public class Horse extends Piece {

    private static final String NAME = "h";

    public Horse(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

    public String getName() {
        return NAME;
    }
}
