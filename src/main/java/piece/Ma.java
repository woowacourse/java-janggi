package piece;

import static pieceProperty.PieceType.MA;

import java.util.List;
import java.util.Objects;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Ma extends Piece {

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

        int dRow = position.calculateDRow(destination);
        int dCol = position.calculateDCol(destination);

        if (position.isUpLeftUp(dRow, dCol)) {
            route.addPosition(position.calculateUpMovement());
        }

        if (position.isUpRightUp(dRow, dCol)) {
            route.addPosition(position.calculateUpMovement());
        }

        if (position.isRightRightUp(dRow, dCol)) {
            route.addPosition(position.calculateRightMovement());
        }

        if (position.isRightRightDown(dRow, dCol)) {
            route.addPosition(position.calculateRightMovement());
        }

        if (position.isDownRightDown(dRow, dCol)) {
            route.addPosition(position.calculateDownMovement());
        }

        if (position.isDownLeftDown(dRow, dCol)) {
            route.addPosition(position.calculateDownMovement());
        }

        if (position.isLeftLeftUp(dRow, dCol)) {
            route.addPosition(position.calculateLeftMovement());
        }

        if (position.isLeftLeftDown(dRow, dCol)) {
            route.addPosition(position.calculateLeftMovement());
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
        return MA;
    }

    @Override
    public Position currentPosition() {
        return position;
    }

    private boolean isInvalidMaMove(final Position destination) {
        return !position.calculateUpRightUPMovement().equals(destination) &&
                !position.calculateUpLeftUpMovement().equals(destination) &&
                !position.calculateDownRightDownMovement().equals(destination) &&
                !position.calculateDownLeftDownMovement().equals(destination) &&
                !position.calculateRightRightUpMovement().equals(destination) &&
                !position.calculateRightRightDownMovement().equals(destination) &&
                !position.calculateLeftLeftUpMovement().equals(destination) &&
                !position.calculateLeftLeftDownMovement().equals(destination);
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
