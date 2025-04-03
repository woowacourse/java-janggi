package movementRule;

import java.util.List;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public class Jol implements PieceRule {

    @Override
    public int getScore() {
        return 1;
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

    private boolean isInvalidJolMove(final Position startPosition, final Position destination) {
        return !startPosition.isDownMovementTo(destination)
                && !startPosition.isLeftMovementTo(destination)
                && !startPosition.isRightMovementTo(destination);
    }

}
