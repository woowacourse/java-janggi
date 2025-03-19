package domain.piece;

import domain.direction.Directions;

public class Cannon extends Piece {

    private static final String NAME = "c";

    public Cannon(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

    public String getName() {
        return NAME;
    }
}
