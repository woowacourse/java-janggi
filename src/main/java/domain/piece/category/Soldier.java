package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.position.Position;

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
    public int getScore() {
        return PieceType.SOLDIER.getScore();
    }

    @Override
    public String getName() {
        return PieceType.SOLDIER.getName();
    }

    @Override
    public boolean isValidPosition(final Position targetPosition) {
        return targetPosition.isValid();
    }

    @Override
    public boolean canMoveInPalace() {
        return true;
    }

    @Override
    public PieceType getType() {
        return PieceType.SOLDIER;
    }
}
