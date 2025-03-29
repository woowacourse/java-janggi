package piece;

import location.PathUtility;
import location.Position;
import store.Pieces;

public class Guard implements Piece{
    private final Position currentPosition;

    public Guard(Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public void validateDestination(Position destination) {
        PathUtility.checkStraightMovement(currentPosition, destination);
        PathUtility.checkOneMovement(currentPosition, destination);
    }

    @Override
    public void validatePaths(Pieces pieces, Position destination) {

    }

    @Override
    public Piece move(Position destination) {
        return new Guard(destination);
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
        return PieceType.GUARD;
    }
}
