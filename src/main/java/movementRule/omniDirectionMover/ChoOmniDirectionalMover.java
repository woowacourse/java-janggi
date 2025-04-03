package movementRule.omniDirectionMover;

import java.util.List;
import movementRule.PieceRule;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public abstract class ChoOmniDirectionalMover implements PieceRule {

    @Override
    public void canMoveTo(final Position startPosition, Position destination) {
        if (isOmniDirectionalMover(startPosition, destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("기물이 움직일 수 없는 위치 입니다."));
        }

        if (!startPosition.isInChoPalace() || !destination.isInChoPalace()) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("초나라 궁성을 벗어날 수 없습니다."));
        }
    }

    @Override
    public Positions makeRoute(final Position startPosition, Position destination) {
        return new Positions(List.of());
    }

    private boolean isOmniDirectionalMover(final Position startPosition, Position destination) {
        return !startPosition.isOneStepDiagonalMoveForChoOmniDirectionMover(destination)
                && !startPosition.isUpMovementTo(destination)
                && !startPosition.isDownMovementTo(destination)
                && !startPosition.isLeftMovementTo(destination)
                && !startPosition.isRightMovementTo(destination);
    }
}
