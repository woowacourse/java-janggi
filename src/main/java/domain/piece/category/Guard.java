package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.spatial.Position;

public class Guard extends Piece {

    public Guard(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Guard updatePosition(final Position position) {
        return new Guard(position, directions);
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
