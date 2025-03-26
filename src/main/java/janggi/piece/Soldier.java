package janggi.piece;

import janggi.position.Position;
import java.util.List;

public class Soldier extends Piece {

    public Soldier(final Color color) {
        super(color);
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        int differenceX = end.x() - start.x();
        int differenceY = end.y() - start.y();
        validateMovingRule(differenceX, differenceY);
        validateDirection(differenceY, color);
        return List.of();
    }

    private void validateMovingRule(final int differenceX, final int differenceY) {
        int absDifferenceX = Math.abs(differenceX);
        int absDifferenceY = Math.abs(differenceY);
        if ((absDifferenceX == 1 && absDifferenceY == 0)
                || (absDifferenceX == 0 && absDifferenceY == 1)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }

    private void validateDirection(final int differenceY, final Color color) {
        if (color == Color.RED && differenceY > 0) {
            throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
        }
        if (color == Color.BLUE && differenceY < 0) {
            throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
        }
    }
}
