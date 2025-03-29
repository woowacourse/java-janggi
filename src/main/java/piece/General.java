package piece;

import game.Team;
import location.Direction;
import location.PathUtility;
import location.Position;

public class General extends Piece {
    private boolean isCatch;
    private Position currentPosition;

    public General(int pieceId, Team team, Position currentPosition) {
        super(pieceId, team, PieceType.ELEPHANT);
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
        checkInPalace(destination);
        PathUtility.checkOneMovement(currentPosition, destination);

        if (Direction.isDiagonal(currentPosition, destination)) {
            PathUtility.checkValidOneDiagonalMovementInPalace(currentPosition, destination);
        }
    }

    @Override
    public void validatePaths(Pieces pieces, Position destination) {

    }

    @Override
    public void move(Position destination) {
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

    private static void checkInPalace(Position destination) {
        if (!PathUtility.isPalacePosition(destination)) {
            throw new IllegalArgumentException("[ERROR] 궁성 외 좌표입니다.");
        }
    }
}
