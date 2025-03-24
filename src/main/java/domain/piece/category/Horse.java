package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;

public class Horse extends Piece {

    private static final PieceType type = PieceType.HORSE;

    public Horse(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

    public Horse(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Horse updatePosition(final Position position) {
        return new Horse(position, directions);
    }

    @Override
    public boolean isEqualType(final PieceType type) {
        return Horse.type == type;
    }

    @Override
    public String getName() {
        return type.getName();
    }
}
