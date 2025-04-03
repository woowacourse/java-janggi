package movementRule.omniDirectionMover;

import java.util.List;
import movementRule.PieceRule;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public abstract class HanOmniDirectionalMover implements PieceRule {
    @Override
    public void canMoveTo(final Position startPosition, Position destination) {
        if (isOmniDirectionalMover(startPosition, destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("기물이 움직일 수 없는 위치 입니다."));
        }

        if (!startPosition.isInHanPalace() || !destination.isInHanPalace()) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("한나라 궁성을 벗어날 수 없습니다."));
        }
    }

    @Override
    public Positions makeRoute(final Position startPosition, Position destination) {
        return new Positions(List.of());
    }

    private boolean isOmniDirectionalMover(final Position startPosition, Position destination) {
        return !startPosition.isOneStepDiagonalMoveForHanOmniDirectionMover(destination)
                && !startPosition.isUpMovementTo(destination)
                && !startPosition.isDownMovementTo(destination)
                && !startPosition.isLeftMovementTo(destination)
                && !startPosition.isRightMovementTo(destination);
    }
}
