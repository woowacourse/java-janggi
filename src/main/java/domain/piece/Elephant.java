package domain.piece;

import domain.direction.Directions;

public class Elephant extends Piece {

    private static final String NAME = "e";

    public Elephant(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

    public Elephant(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Elephant updatePosition(final Position position) {
        return new Elephant(position, directions);
    }

    public String getName() {
        return NAME;
    }
}
