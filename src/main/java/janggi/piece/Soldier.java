package janggi.piece;

import janggi.position.Direction;
import janggi.position.Position;
import java.util.List;

public class Soldier extends Piece {

    public Soldier(final Color color) {
        super(color);
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        validateMovingRule(start, end);
        validateDirection(start, end);
        return List.of();
    }

    private void validateMovingRule(final Position start, final Position end) {
        int absDifferenceX = start.calculateAbsoluteDifferenceX(end);
        int absDifferenceY = start.calculateAbsoluteDifferenceY(end);
        if ((absDifferenceX == 1 && absDifferenceY == 0)
                || (absDifferenceX == 0 && absDifferenceY == 1)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }

    private void validateDirection(final Position start, final Position end) {
        final Direction direction = start.calculateDirection(end);
        if (color.isReverseFrontVerticalDirection(direction)) {
            throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
        }
    }

}
