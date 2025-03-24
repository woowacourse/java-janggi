package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;

public class Guard extends Piece {

    private static final PieceType type = PieceType.GUARD;

    public Guard(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

    public Guard(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Guard updatePosition(final Position position) {
        return new Guard(position, directions);
    }

    @Override
    public boolean isEqualType(final PieceType type) {
        return Guard.type == type;
    }

    @Override
    public String getName() {
        return type.getName();
    }
}
