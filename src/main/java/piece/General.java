package piece;

import game.Team;
import location.Direction;
import location.PathManager;
import location.Position;

public class General extends Piece {
    private final PathManager pathManager;
    private Position currentPosition;

    public General(int id, Team team, PathManager pathManager, Position currentPosition) {
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
        return PieceType.GENERAL;
    }

    @Override
    public int getScore() {
        return 0;
    }

    @Override
    public void validateDestination(Position destination) {
        checkInPalace(destination);
        pathManager.checkOneMovement(currentPosition, destination);

        if (Direction.isDiagonal(currentPosition, destination)) {
            pathManager.checkValidOneDiagonalMovementInPalace(currentPosition, destination);
        }
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

    private void checkInPalace(Position destination) {
        if (!pathManager.isPalacePosition(destination)) {
            throw new IllegalArgumentException("[ERROR] 궁성 외 좌표입니다.");
        }
    }
}
