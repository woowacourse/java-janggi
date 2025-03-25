package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;

public class Chariot extends Piece {

    public Chariot(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Chariot updatePosition(final Position position) {
        return new Chariot(position, directions);
    }

    @Override
    public boolean isEqualType(final PieceType type) {
        return PieceType.CHARIOT == type;
    }

    @Override
    public String getName() {
        return PieceType.CHARIOT.getName();
    }
}
