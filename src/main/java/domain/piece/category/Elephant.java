package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.Position;

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
