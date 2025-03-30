package piece;

import game.Team;
import location.Direction;
import location.PathUtility;
import location.Position;

public class Guard extends Piece {
    private boolean isCatch;
    private Position currentPosition;

    public Guard(int pieceId, Team team, Position currentPosition) {
        super(pieceId, team, PieceType.GUARD);
        this.isCatch = false;
        this.currentPosition = currentPosition;
    }

    @Override
    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public boolean isCatch() {
        return isCatch;
    }

    @Override
    public void validateDestination(Position destination) {
        PathUtility.checkOneMovement(currentPosition, destination);

        if (PathUtility.isPalacePosition(currentPosition)
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
    public void updateCurrentPosition(Position destination) {
        currentPosition = destination;
    }

    @Override
    public void catchByOpponent() {
        isCatch = true;
    }

    @Override
    public boolean isPlacedAt(Position targetPosition) {
        return currentPosition.equals(targetPosition);
    }
}
