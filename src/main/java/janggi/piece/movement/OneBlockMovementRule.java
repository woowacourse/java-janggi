package janggi.piece.movement;

import janggi.position.Direction;
import janggi.position.Position;
import java.util.List;

public class OneBlockMovementRule implements MovementRule {

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        return List.of();
    }

    @Override
    public void validateMovementRule(final Position start, final Position end) {
        validateDirection(start, end);
        validateMoveDistance(start, end);
    }

    private void validateDirection(final Position start, final Position end) {
        final Direction direction = start.calculateDirection(end);
        if (start.hasDirection(direction)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }

    private void validateMoveDistance(final Position start, final Position end) {
        if(start.isMoveDistanceOneBlock(end)){
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}
