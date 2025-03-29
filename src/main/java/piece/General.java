package piece;

import location.Direction;
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
        checkInPalace(destination);
        PathUtility.checkOneMovement(currentPosition, destination);

        if(Direction.isDiagonal(currentPosition, destination)) {
            PathUtility.checkValidOneDiagonalMovementInPalace(currentPosition, destination);
        }
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

    private static void checkInPalace(Position destination) {
        if (!PathUtility.isPalacePosition(destination)) {
            throw new IllegalArgumentException("[ERROR] 궁성 외 좌표입니다.");
        }
    }
}
