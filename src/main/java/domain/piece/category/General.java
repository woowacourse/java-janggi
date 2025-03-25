package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;

public class General extends Piece {

    public General(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public General updatePosition(final Position position) {
        return new General(position, directions);
    }

    @Override
    public boolean isEqualType(final PieceType type) {
        return PieceType.GENERAL == type;
    }

    @Override
    public String getName() {
        return PieceType.GENERAL.getName();
    }
}
