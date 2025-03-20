package domain.piece;

import domain.direction.Directions;

public class Pawn extends Piece {

    private static final String NAME = "p";

    public Pawn(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

    public Pawn(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Pawn updatePosition(final Position position) {
        return new Pawn(position, directions);
    }

    @Override
    public boolean isKing() {
        return false;
    }

    public String getName() {
        return NAME;
    }
}
