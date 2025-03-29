package piece;

import location.Direction;
import location.PathUtility;
import location.Position;
import store.Pieces;

public class Guard implements Piece{
    private final PieceType pieceType;
    private final Position currentPosition;

    public Guard(Position currentPosition) {
        this.pieceType = PieceType.GUARD;
        this.currentPosition = currentPosition;
    }

    @Override
    public void validateDestination(Position destination) {
        PathUtility.checkOneMovement(currentPosition, destination);

        if(PathUtility.isPalacePosition(currentPosition)
                && Direction.isDiagonal(currentPosition, destination)) {
            PathUtility.checkValidOneDiagonalMovementInPalace(currentPosition, destination);
            return;
        }
        PathUtility.checkStraightMovement(currentPosition, destination);
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
        return pieceType;
    }

    @Override
    public int getScore() {
        return pieceType.getScore();
    }
}
