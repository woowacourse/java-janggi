package movementRule;

import static pieceProperty.PieceType.BYEONG;

import java.util.List;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Byeong implements PieceRule {

    @Override
    public int getScore() {
        return 1;
    }

    @Override
    public void canMoveTo(final Position startPosition, final Position destination) {
        if (isInvalidByeongMove(startPosition, destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("기물이 움직일 수 없는 위치입니다."));
        }
    }

    @Override
    public Positions makeRoute(final Position startPosition, final Position position) {
        return new Positions(List.of());
    }

    @Override
    public PieceType getPieceType() {
        return BYEONG;
    }

    @Override
    public boolean isPo() {
        return false;
    }

    @Override
    public boolean isJanggun() {
        return false;
    }

    private boolean isInvalidByeongMove(final Position startPosition, final Position destination) {
        return !startPosition.isUpMovementTo(destination)
                && !startPosition.isLeftMovementTo(destination)
                && !startPosition.isRightMovementTo(destination);
    }


}
