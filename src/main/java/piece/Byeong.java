package piece;

import static pieceProperty.PieceType.BYEONG;

import java.util.List;
import java.util.Objects;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Byeong implements Piece {

    private Position position;

    public Byeong(Position position) {
        this.position = position;
    }

    @Override
    public void canMoveTo(final Position destination) {
        if (isInvalidByeongMove(destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("기물이 움직일 수 없는 위치입니다."));
        }
    }

    @Override
    public Positions makeRoute(final Position position) {
        return new Positions(List.of());
    }

    @Override
    public PieceType getPieceType() {
        return BYEONG;
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
    public Position currentPosition() {
        return position;
    }

    private boolean isInvalidByeongMove(final Position destination) {
        return !position.isDownMovementTo(destination)
                && !position.isLeftMovementTo(destination)
                && !position.isRightMovementTo(destination);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Byeong byeong = (Byeong) o;
        return Objects.equals(position, byeong.position);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(position);
    }

}
