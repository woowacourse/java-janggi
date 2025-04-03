package movementRule;

import java.util.List;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Ma implements PieceRule {

    @Override
    public int getScore() {
        return 5;
    }

    @Override
    public void canMoveTo(final Position startPosition, final Position destination) {
        if (isInvalidMaMove(startPosition, destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("기물이 움직일 수 없는 위치입니다."));
        }
    }

    @Override
    public Positions makeRoute(final Position startPosition, final Position destination) {
        Positions route = new Positions(List.of());

        if (startPosition.isUpLeftUpMovementTo(destination)) {
            route.addPosition(startPosition.calculateUpMovement());
        }

        if (startPosition.isUpRightUpMovementTo(destination)) {
            route.addPosition(startPosition.calculateUpMovement());
        }

        if (startPosition.isRightRightUpMovementTo(destination)) {
            route.addPosition(startPosition.calculateRightMovement());
        }

        if (startPosition.isRightRightDownMovementTo(destination)) {
            route.addPosition(startPosition.calculateRightMovement());
        }

        if (startPosition.isDownRightDownMovementTo(destination)) {
            route.addPosition(startPosition.calculateDownMovement());
        }

        if (startPosition.isDownLeftDownMovementTo(destination)) {
            route.addPosition(startPosition.calculateDownMovement());
        }

        if (startPosition.isLeftLeftUpMovementTo(destination)) {
            route.addPosition(startPosition.calculateLeftMovement());
        }

        if (startPosition.isLeftLeftDownMovementTo(destination)) {
            route.addPosition(startPosition.calculateLeftMovement());
        }

        return route;
    }

    private boolean isInvalidMaMove(final Position startPosition, final Position destination) {
        return !startPosition.isUpRightUpMovementTo(destination)
                && !startPosition.isUpLeftUpMovementTo(destination)
                && !startPosition.isRightRightUpMovementTo(destination)
                && !startPosition.isRightRightDownMovementTo(destination)
                && !startPosition.isDownRightDownMovementTo(destination)
                && !startPosition.isDownLeftDownMovementTo(destination)
                && !startPosition.isLeftLeftUpMovementTo(destination)
                && !startPosition.isLeftLeftDownMovementTo(destination);
    }

}
