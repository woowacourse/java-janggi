package janggi.piece;

import janggi.Position;
import janggi.Side;
import java.util.List;

public class Elephant extends Piece {

    public Elephant(final Side side) {
        super(side);
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        int differenceX = end.x() - start.x();
        int differenceY = end.y() - start.y();
        validateMovingRule(differenceX, differenceY);
        Position firstStep = calculateFirstDirection(start, differenceX, differenceY);
        Position secondStep = calculateSecondDirection(start, differenceX, differenceY);
        return List.of(firstStep, secondStep);
    }

    private Position calculateFirstDirection(final Position start, final int differenceX, final int differenceY) {
        if (differenceX == 3) {
            return start.right(1);
        }
        if (differenceX == -3) {
            return start.left(1);
        }
        if (differenceY == 3) {
            return start.up(1);
        }
        return start.down(1);
    }

    private Position calculateSecondDirection(final Position start, final int differenceX, final int differenceY) {
        if (differenceX == 3) {
            // 양수인 경우 위로 가는거임
            if (differenceY == 2) {
                return start.right(2).up(1);
            }
            // 음수인 경우 아래로 가는 거임
            return start.right(2).down(1);

        }
        if (differenceX == -3) {
            // 양수인 경우 위로 가는거임
            if (differenceY == 2) {
                return start.left(2).up(1);
            }
            // 음수인 경우 아래로 가는 거임
            return start.left(2).down(1);

        }
        if (differenceY == 3) {
            if (differenceX == 2) {
                // 오른쪽임
                return start.up(2).right(1);
            }
            // 왼쪽임
            return start.up(2).left(1);

        }

        if (differenceX == 2) {
            // 오른쪽임
            return start.down(2).right(1);
        }
        // 왼쪽임
        return start.down(2).left(1);
    }

    private void validateMovingRule(final int differenceX, final int differenceY) {
        int absDifferenceX = Math.abs(differenceX);
        int absDifferenceY = Math.abs(differenceY);
        if ((absDifferenceX == 3 && absDifferenceY == 2) || (absDifferenceX == 2 && absDifferenceY == 3)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}
