package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.Position;

public class Soldier extends Piece {

    private static final String NAME = "p";

    public Soldier(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Soldier updatePosition(final Position position) {
        return new Soldier(position, directions);
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
