package piece;

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
        PathUtility.checkStraightOneMovement(currentPosition, destination);
        checkInPalace(destination);
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

    private void checkInPalace(Position destination) {
        if(destination.x() < 4 || 6 < destination.x()) {
            throw new IllegalArgumentException("[ERROR] 장군은 궁성 바깥으로 이동할 수 없습니다.");
        }
        if(4 <= destination.y() && destination.y() <= 7 ) {
            throw new IllegalArgumentException("[ERROR] 장군은 궁성 바깥으로 이동할 수 없습니다.");
        }
    }
}
