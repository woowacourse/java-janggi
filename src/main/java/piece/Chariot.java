package piece;

import game.Team;
import location.Direction;
import location.PathUtility;
import location.Position;
import java.util.List;

public class Chariot extends Piece {
    private boolean isCatch;
    private Position currentPosition;

    public Chariot(int pieceId, Team team, Position currentPosition) {
        super(pieceId, team, PieceType.CHARIOT);
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
        if (PathUtility.isPalacePosition(currentPosition)
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
