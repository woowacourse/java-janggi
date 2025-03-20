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
        if ((Math.abs(differenceX) == 1 && Math.abs(differenceY) == 0)
                || (Math.abs(differenceX) == 0 && Math.abs(differenceY) == 1)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}
