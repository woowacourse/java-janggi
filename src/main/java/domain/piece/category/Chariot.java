package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.Position;

public class Chariot extends Piece {

    private static final String NAME = "r";

    public Chariot(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Chariot updatePosition(final Position position) {
        return new Chariot(position, directions);
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
