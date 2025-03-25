package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;

public class Elephant extends Piece {

    public Elephant(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Elephant updatePosition(final Position position) {
        return new Elephant(position, directions);
    }

    @Override
    public boolean isEqualType(final PieceType type) {
        return PieceType.ELEPHANT == type;
    }

    @Override
    public String getName() {
        return PieceType.ELEPHANT.getName();
    }
}
