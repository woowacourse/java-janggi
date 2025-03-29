package movementRule;

import static pieceProperty.PieceType.JOL;

import java.util.List;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Jol implements PieceRule {

    @Override
    public boolean isPo() {
        return false;
    }

    @Override
    public boolean isJanggun() {
        return false;
    }

    @Override
    public void canMoveTo(final Position startPosition, final Position destination) {
        if (isInvalidJolMove(startPosition, destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("기물이 움직일 수 없는 위치입니다."));
        }
    }

    @Override
    public Positions makeRoute(final Position startPosition, final Position position) {
        return new Positions(List.of());
    }

    @Override
    public PieceType getPieceType() {
        return JOL;
    }

    private boolean isInvalidJolMove(final Position startPosition, final Position destination) {
        return !startPosition.isDownMovementTo(destination)
                && !startPosition.isLeftMovementTo(destination)
                && !startPosition.isRightMovementTo(destination);
    }

}
