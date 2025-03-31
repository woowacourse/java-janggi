package domain.piece.category;

import domain.direction.Directions;
import domain.piece.Piece;
import domain.position.Position;

public class Chariot extends Piece {

    public Chariot(final Position position, final Directions directions) {
        super(position, directions);
    }

    @Override
    public Chariot updatePosition(final Position position) {
        return new Chariot(position, directions);
    }

    @Override
    public boolean isEqualType(final PieceType type) {
        return PieceType.CHARIOT == type;
    }

    @Override
    public int getScore() {
        return PieceType.CHARIOT.getScore();
    }

    @Override
    public String getName() {
        return PieceType.CHARIOT.getName();
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
        return PieceType.CHARIOT;
    }
}
