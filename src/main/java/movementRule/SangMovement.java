package movementRule;

import pieceProperty.Position;

public class SangMovement {

    public boolean isInvalidSangMove(Position startPosition, Position destination) {
        return !startPosition.isUpRightUpRightUpMovementTo(destination)
                && !startPosition.isUpLeftUpLeftUpMovementTo(destination)
                && !startPosition.isRightRightUpRightUpMovementTo(destination)
                && !startPosition.isRightRightDownRightDownMovementTo(destination)
                && !startPosition.isDownRightDownRightDownMovementTo(destination)
                && !startPosition.isDownLeftDownLeftDownMovementTo(destination)
                && !startPosition.isLeftLeftUpLeftUpMovementTo(destination)
                && !startPosition.isLeftLeftDownLeftDownMovementTo(destination);
    }
}
