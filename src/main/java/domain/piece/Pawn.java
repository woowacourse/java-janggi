package domain.piece;

import domain.direction.Directions;

public class Pawn extends Piece {

    private static final String NAME = "p";

    public Pawn(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

    public String getName() {
        return NAME;
    }
}
