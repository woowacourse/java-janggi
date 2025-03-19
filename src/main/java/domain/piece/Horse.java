package domain.piece;

import domain.direction.Directions;

public class Horse extends Piece {

    private static final String NAME = "h";

    public Horse(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

    public Horse(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Horse updatePosition(final Position position) {
        return new Horse(position, directions);
    }

    public String getName() {
        return NAME;
    }
}
