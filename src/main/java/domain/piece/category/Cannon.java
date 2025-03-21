package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.Position;

public class Cannon extends Piece {

    private static final String NAME = "c";

    public Cannon(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Cannon updatePosition(final Position position) {
        return new Cannon(position, directions);
    }

    @Override
    public boolean isKing() {
        return false;
    }

    @Override
    public boolean isCannon() {
        return true;
    }

    public String getName() {
        return NAME;
    }
}
