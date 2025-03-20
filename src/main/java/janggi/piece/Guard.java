package janggi.piece;

import janggi.Position;
import janggi.Side;
import java.util.List;

public class Guard extends Piece {

    public Guard(final Side side) {
        super(side);
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        int differenceX = end.x() - start.x();
        int differenceY = end.y() - start.y();
        validateMovingRule(differenceX, differenceY);
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
}
