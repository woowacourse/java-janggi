package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;

public class Chariot extends Piece {

    private static final PieceType type = PieceType.CHARIOT;

    public Chariot(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

    public Chariot(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Chariot updatePosition(final Position position) {
        return new Chariot(position, directions);
    }

    @Override
    public boolean isEqualType(final PieceType type) {
        return Chariot.type == type;
    }

    @Override
    public String getName() {
        return type.getName();
    }
}
