package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.position.Position;

public class Elephant extends Piece {

    public Elephant(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Elephant updatePosition(final Position position) {
        return new Elephant(position, directions);
    }

    @Override
    public boolean isEqualType(final PieceType type) {
        return PieceType.ELEPHANT == type;
    }

    @Override
    public int getScore() {
        return PieceType.ELEPHANT.getScore();
    }

    @Override
    public String getName() {
        return PieceType.ELEPHANT.getName();
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
        return PieceType.ELEPHANT;
    }
}
