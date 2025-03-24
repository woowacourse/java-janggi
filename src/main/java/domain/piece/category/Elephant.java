package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;

public class Elephant extends Piece {

    private static final PieceType type = PieceType.ELEPHANT;

    public Elephant(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

    public Elephant(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Elephant updatePosition(final Position position) {
        return new Elephant(position, directions);
    }

    @Override
    public boolean isEqualType(final PieceType type) {
        return Elephant.type == type;
    }

    @Override
    public String getName() {
        return type.getName();
    }
}
