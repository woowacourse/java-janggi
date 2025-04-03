package piece;

import game.Team;
import location.Direction;
import location.PathManager;
import location.Position;

public class Guard extends Piece {
    private final PathManager pathManager;
    private Position currentPosition;

    public Guard(int id, Team team, PathManager pathManager, Position currentPosition) {
        super(id, team);
        this.pathManager = pathManager;
        this.currentPosition = currentPosition;
    }

    @Override
    public Position getCurrentPosition() {
        return currentPosition;
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.GUARD;
    }

    @Override
    public int getScore() {
        return 3;
    }

    @Override
    public void validateDestination(Position destination) {
        pathManager.checkOneMovement(currentPosition, destination);

        if (pathManager.isPalacePosition(currentPosition)
                && Direction.isDiagonal(currentPosition, destination)) {
            pathManager.checkValidOneDiagonalMovementInPalace(currentPosition, destination);
            return;
        }
        pathManager.checkStraightMovement(currentPosition, destination);
    }

    @Override
    public void validatePaths(Pieces pieces, Position destination) {

    }

    @Override
    public void updateCurrentPosition(Position destination) {
        currentPosition = destination;
    }

    @Override
    public boolean isPlacedAt(Position targetPosition) {
        return currentPosition.equals(targetPosition);
    }
}
