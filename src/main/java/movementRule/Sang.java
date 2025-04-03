package movementRule;

import java.util.List;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Sang implements PieceRule {

    @Override
    public int getScore() {
        return 5;
    }

    @Override
    public void canMoveTo(final Position startPosition, final Position destination) {
        if (isInvalidSangMove(startPosition, destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("기물이 움직일 수 없는 위치입니다."));
        }
    }

    @Override
    public Positions makeRoute(final Position startPosition, final Position destination) {
        Positions route = new Positions(List.of());

        if (startPosition.isUpLeftUpLeftUpMovementTo(destination)) {
            route.addPosition(startPosition.calculateUpMovement());
            route.addPosition(startPosition.calculateUpLeftUpMovement());
        }

        if (startPosition.isUpRightUpRightUpMovementTo(destination)) {
            route.addPosition(startPosition.calculateUpMovement());
            route.addPosition(startPosition.calculateUpRightUPMovement());
        }

        if (startPosition.isRightRightUpRightUpMovementTo(destination)) {
            route.addPosition(startPosition.calculateRightMovement());
            route.addPosition(startPosition.calculateRightRightUpMovement());
        }

        if (startPosition.isRightRightDownRightDownMovementTo(destination)) {
            route.addPosition(startPosition.calculateRightMovement());
            route.addPosition(startPosition.calculateRightRightDownMovement());
        }

        if (startPosition.isDownLeftDownLeftDownMovementTo(destination)) {
            route.addPosition(startPosition.calculateDownMovement());
            route.addPosition(startPosition.calculateDownLeftDownMovement());
        }

        if (startPosition.isDownRightDownRightDownMovementTo(destination)) {
            route.addPosition(startPosition.calculateDownMovement());
            route.addPosition(startPosition.calculateDownRightDownMovement());
        }

        if (startPosition.isLeftLeftUpLeftUpMovementTo(destination)) {
            route.addPosition(startPosition.calculateLeftMovement());
            route.addPosition(startPosition.calculateLeftLeftUpMovement());
        }

        if (startPosition.isLeftLeftDownLeftDownMovementTo(destination)) {
            route.addPosition(startPosition.calculateLeftMovement());
            route.addPosition(startPosition.calculateLeftLeftDownMovement());
        }

        return route;
    }

    private boolean isInvalidSangMove(Position startPosition, final Position destination) {
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
