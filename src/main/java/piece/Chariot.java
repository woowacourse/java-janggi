package piece;

import game.Team;
import location.Direction;
import location.PathManager;
import location.Position;
import java.util.List;

public class Chariot extends Piece {
    private final PathManager pathManager;
    private Position currentPosition;

    public Chariot(int id, Team team, PathManager pathManager, Position currentPosition) {
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
        return PieceType.CHARIOT;
    }

    @Override
    public int getScore() {
        return 13;
    }

    @Override
    public void validateDestination(Position destination) {
        if (pathManager.isPalacePosition(currentPosition)
                && Direction.isDiagonal(currentPosition, destination)) {
            pathManager.checkValidTwoDiagonalMovementInPalace(currentPosition, destination);
            return;
        }
        pathManager.checkStraightMovement(currentPosition, destination);
    }

    @Override
    public void validatePaths(Pieces pieces, Position destination) {
        List<Position> paths = pathManager.calculateOneDirectionPaths(currentPosition, destination);
        paths.forEach(pieces::checkNotExistedPieceInPosition);
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
