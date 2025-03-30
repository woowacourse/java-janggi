package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.piece.PieceType;
import domain.piece.Position;

public class Horse extends Piece {

    public Horse(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Horse updatePosition(final Position position) {
        return new Horse(position, directions);
    }

    @Override
    public boolean isEqualType(final PieceType type) {
        return PieceType.HORSE == type;
    }

    @Override
    public int getScore() {
        return PieceType.HORSE.getScore();
    }

    @Override
    public String getName() {
        return PieceType.HORSE.getName();
    }

    @Override
    public boolean isValidPosition(final Position targetPosition) {
        return targetPosition.isValid();
    }

    @Override
    public boolean canMoveInPalace() {
        return false;
    }

    @Override
    public PieceType getType() {
        return PieceType.HORSE;
    }
}
