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

        int dRow = position.calculateDRow(destination);
        int dCol = position.calculateDCol(destination);

        if (position.isUpLeftUpLeftUp(dRow, dCol)) {
            route.addPosition(position.calculateUpMovement());
            route.addPosition(position.calculateUpLeftUpMovement());
        }

        if (position.isUpRightUpRightUp(dRow, dCol)) {
            route.addPosition(position.calculateUpMovement());
            route.addPosition(position.calculateUpRightUPMovement());
        }

        if (position.isRightUpRightUpRight(dRow, dCol)) {
            route.addPosition(position.calculateRightMovement());
            route.addPosition(position.calculateRightRightUpMovement());
        }

        if (position.isRightRightDownRightDown(dRow, dCol)) {
            route.addPosition(position.calculateRightMovement());
            route.addPosition(position.calculateRightRightDownMovement());
        }

        if (position.isDownLeftDownLeftDown(dRow, dCol)) {
            route.addPosition(position.calculateDownMovement());
            route.addPosition(position.calculateDownLeftDownMovement());
        }

        if (position.isDownRightDownRightDown(dRow, dCol)) {
            route.addPosition(position.calculateDownMovement());
            route.addPosition(position.calculateDownRightDownMovement());
        }

        if (position.isLeftLeftUpLeftUp(dRow, dCol)) {
            route.addPosition(position.calculateLeftMovement());
            route.addPosition(position.calculateLeftLeftUpMovement());
        }

        if (position.isLeftLeftDownLeftDown(dRow, dCol)) {
            route.addPosition(position.calculateLeftMovement());
            route.addPosition(position.calculateLeftLeftDownMovement());
        }

        return route;
    }

    @Override
    public boolean isJanggun() {
        return false;
    }

    @Override
    public boolean isPo() {
        return false;
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
        return !position.calculateUpRightUpRightUpMovement().equals(destination) &&
                !position.calculateUpLeftUpLeftUpMovement().equals(destination) &&
                !position.calculateRightRightUpRightUpMovement().equals(destination) &&
                !position.calculateRightRightDownRightDownMovement().equals(destination) &&
                !position.calculateDownRightDownRightDownMovement().equals(destination) &&
                !position.calculateDownLeftDownLeftDownMovement().equals(destination) &&
                !position.calculateLeftLeftUpLeftUpMovement().equals(destination) &&
                !position.calculateLeftLeftDownLeftDownMovement().equals(destination);
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
