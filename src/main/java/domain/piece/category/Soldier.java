package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;

public class Soldier extends Piece {

    public Soldier(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Soldier updatePosition(final Position position) {
        return new Soldier(position, directions);
    }

    @Override
    public boolean isEqualType(final PieceType type) {
        return PieceType.SOLDIER == type;
    }

    @Override
    public String getName() {
        return PieceType.SOLDIER.getName();
    }
}
