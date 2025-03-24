package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.spatial.Position;

public class Soldier extends Piece {

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
}
