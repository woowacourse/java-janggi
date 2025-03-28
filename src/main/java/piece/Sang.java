package piece;

import static pieceProperty.PieceType.SANG;

import java.util.List;
import java.util.Objects;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Sang implements Piece {

    private Position position;

    public Sang(Position position) {
        this.position = position;
    }

    @Override
    public boolean isSamePosition(final Position startPosition) {
        return startPosition.equals(position);
    }

    @Override
    public void updateChessPiecePositionBy(final Position destination) {
        position = destination;
    }

    @Override
    public void canMoveTo(final Position destination) {
        if (isInvalidSangMove(destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("기물이 움직일 수 없는 위치입니다."));
        }
    }

    @Override
    public Positions makeRoute(final Position destination) {
        Positions route = new Positions(List.of());

        if (position.isUpLeftUpLeftUpMovementTo(destination)) {
            route.addPosition(position.calculateUpMovement());
            route.addPosition(position.calculateUpLeftUpMovement());
        }

        if (position.isUpRightUpRightUpMovementTo(destination)) {
            route.addPosition(position.calculateUpMovement());
            route.addPosition(position.calculateUpRightUPMovement());
        }

        if (position.isRightRightUpRightUpMovementTo(destination)) {
            route.addPosition(position.calculateRightMovement());
            route.addPosition(position.calculateRightRightUpMovement());
        }

        if (position.isRightRightDownRightDownMovementTo(destination)) {
            route.addPosition(position.calculateRightMovement());
            route.addPosition(position.calculateRightRightDownMovement());
        }

        if (position.isDownLeftDownLeftDownMovementTo(destination)) {
            route.addPosition(position.calculateDownMovement());
            route.addPosition(position.calculateDownLeftDownMovement());
        }

        if (position.isDownRightDownRightDownMovementTo(destination)) {
            route.addPosition(position.calculateDownMovement());
            route.addPosition(position.calculateDownRightDownMovement());
        }

        if (position.isLeftLeftUpLeftUpMovementTo(destination)) {
            route.addPosition(position.calculateLeftMovement());
            route.addPosition(position.calculateLeftLeftUpMovement());
        }

        if (position.isLeftLeftDownLeftDownMovementTo(destination)) {
            route.addPosition(position.calculateLeftMovement());
            route.addPosition(position.calculateLeftLeftDownMovement());
        }

        return route;
    }

    @Override
    public PieceType getPieceType() {
        return SANG;
    }

    @Override
    public Position currentPosition() {
        return position;
    }

    private boolean isInvalidSangMove(final Position destination) {
        return !position.isUpRightUpRightUpMovementTo(destination)
                && !position.isUpLeftUpLeftUpMovementTo(destination)
                && !position.isRightRightUpRightUpMovementTo(destination)
                && !position.isRightRightDownRightDownMovementTo(destination)
                && !position.isDownRightDownRightDownMovementTo(destination)
                && !position.isDownLeftDownLeftDownMovementTo(destination)
                && !position.isLeftLeftUpLeftUpMovementTo(destination)
                && !position.isLeftLeftDownLeftDownMovementTo(destination);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Sang sang = (Sang) o;
        return Objects.equals(position, sang.position);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }
}
