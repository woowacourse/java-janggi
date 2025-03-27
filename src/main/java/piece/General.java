package piece;

import location.PathUtility;
import location.Position;
import store.Pieces;

public class General implements Piece {
    private final Position currentPosition;

    public General(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public void validateDestination(Position destination) {
        PathUtility.checkStraightOneMovement(currentPosition, destination);
    }

    @Override
    public void validatePaths(Pieces pieces, Position destination) {

    }

    @Override
    public Piece move(Position destination) {
        return new General(destination);
    }

    @Override
    public boolean isPlacedAt(Position targetPosition) {
        return currentPosition.equals(targetPosition);
    }

    @Override
    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.GENERAL;
    }
}
