package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;

public class Soldier extends Piece {

    private static final PieceType type = PieceType.SOLDIER;

    public Soldier(final int row, final int column, final Directions directions) {
        super(row, column, directions);
    }

    public Soldier(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Soldier updatePosition(final Position position) {
        return new Soldier(position, directions);
    }

    @Override
    public boolean isEqualType(final PieceType type) {
        return Soldier.type == type;
    }

    @Override
    public String getName() {
        return type.getName();
    }
}
