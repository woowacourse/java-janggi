package domain.piece;

import domain.direction.Directions;

public class Rook extends Piece {

    private static final String NAME = "r";

    public Rook(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

    public Rook(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Rook updatePosition(final Position position) {
        return new Rook(position, directions);
    }

    @Override
    public boolean isKing() {
        return false;
    }

    public String getName() {
        return NAME;
    }
}
