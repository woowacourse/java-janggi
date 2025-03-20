package domain.piece;

import domain.direction.Directions;

public class Advisor extends Piece {

    private static final String NAME = "a";

    public Advisor(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

    public Advisor(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Advisor updatePosition(final Position position) {
        return new Advisor(position, directions);
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isCannon() {
        return false;
    }

    public String getName() {
        return NAME;
    }
}
