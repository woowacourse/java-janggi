package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;

public class Cannon extends Piece {

    public Cannon(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Cannon updatePosition(final Position position) {
        return new Cannon(position, directions);
    }

    @Override
    public boolean isEqualType(final PieceType type) {
        return PieceType.CANNON == type;
    }

    @Override
    public String getName() {
        return PieceType.CANNON.getName();
    }
}
