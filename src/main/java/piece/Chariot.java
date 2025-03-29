package piece;

import location.Direction;
import location.PathUtility;
import location.Position;
import java.util.List;
import store.Pieces;

public class Chariot implements Piece {
    private final Position currentPosition;

    public Chariot( Position currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public void validateDestination(Position destination) {
        if(PathUtility.isPalacePosition(currentPosition)
                && Direction.isDiagonal(currentPosition, destination)) {
            PathUtility.checkValidTwoDiagonalMovementInPalace(currentPosition, destination);
            return;
        }
        PathUtility.checkStraightMovement(currentPosition, destination);
    }

    @Override
    public void validatePaths(Pieces pieces, Position destination) {
        List<Position> paths = PathUtility.calculateOneDirectionPaths(currentPosition, destination);
        paths.forEach(pieces::checkNotExistedPieceInPosition);
    }

    @Override
    public Piece move(Position destination) {
        return new Chariot(destination);
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
        return PieceType.CHARIOT;
    }
}
