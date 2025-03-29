package movementRule.omniDirectionMover;

import java.util.List;
import movementRule.PieceRule;
import pieceProperty.PieceType;
import pieceProperty.Position;
import pieceProperty.Positions;
import view.ErrorMessage;

public abstract sealed class OmniDirectionalMover
        implements PieceRule
        permits Sa, Janggun {

    @Override
    public void canMoveTo(final Position startPosition, Position destination) {
        if (isOmniDirectionalMover(startPosition, destination)) {
            throw new IllegalArgumentException(ErrorMessage.formatMessage("기물이 움직일 수 없는 위치 입니다."));
        }
    }

    @Override
    public Positions makeRoute(final Position startPosition, Position destination) {
        return new Positions(List.of());
    }

    @Override
    public abstract PieceType getPieceType();

    private boolean isOmniDirectionalMover(final Position startPosition, Position destination) {
        return !startPosition.isUpMovementTo(destination)
                && !startPosition.isDownMovementTo(destination)
                && !startPosition.isLeftMovementTo(destination)
                && !startPosition.isRightMovementTo(destination);
    }

}
