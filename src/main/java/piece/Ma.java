package piece;

import static pieceProperty.PieceType.MA;

import java.util.List;
import java.util.Objects;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Ma implements Piece {

    private Position position;

    public Ma(Position position) {
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
        if (isInvalidMaMove(destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("기물이 움직일 수 없는 위치입니다."));
        }
    }

    @Override
    public Positions makeRoute(final Position destination) {
        Positions route = new Positions(List.of());

        if (position.isUpLeftUpMovementTo(destination)) {
            route.addPosition(position.calculateUpMovement());
        }

        if (position.isUpRightUpMovementTo(destination)) {
            route.addPosition(position.calculateUpMovement());
        }

        if (position.isRightRightUpMovementTo(destination)) {
            route.addPosition(position.calculateRightMovement());
        }

        if (position.isRightRightDownMovementTo(destination)) {
            route.addPosition(position.calculateRightMovement());
        }

        if (position.isDownRightDownMovementTo(destination)) {
            route.addPosition(position.calculateDownMovement());
        }

        if (position.isDownLeftDownMovementTo(destination)) {
            route.addPosition(position.calculateDownMovement());
        }

        if (position.isLeftLeftUpMovementTo(destination)) {
            route.addPosition(position.calculateLeftMovement());
        }

        if (position.isLeftLeftDownMovementTo(destination)) {
            route.addPosition(position.calculateLeftMovement());
        }

        return route;
    }

    @Override
    public PieceType getPieceType() {
        return MA;
    }

    @Override
    public Position currentPosition() {
        return position;
    }

    private boolean isInvalidMaMove(final Position destination) {
        return !position.isUpRightUpMovementTo(destination)
                && !position.isUpLeftUpMovementTo(destination)
                && !position.isRightRightUpMovementTo(destination)
                && !position.isRightRightDownMovementTo(destination)
                && !position.isDownRightDownMovementTo(destination)
                && !position.isDownLeftDownMovementTo(destination)
                && !position.isLeftLeftUpMovementTo(destination)
                && !position.isLeftLeftDownMovementTo(destination);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ma ma = (Ma) o;
        return Objects.equals(position, ma.position);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }
}
