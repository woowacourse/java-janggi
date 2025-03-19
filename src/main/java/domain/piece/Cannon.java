package domain.piece;

import domain.direction.Directions;

public class Cannon extends Piece {

    private static final String NAME = "c";

    public Cannon(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

    public Cannon(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Cannon updatePosition(final Position position) {
        return new Cannon(position, directions);
    }

    public String getName() {
        return NAME;
    }
}
