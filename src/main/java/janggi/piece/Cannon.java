package janggi.piece;

import janggi.Position;
import janggi.Side;
import java.util.List;
import java.util.stream.IntStream;

public class Cannon extends Piece {

    public Cannon(Side side) {
        super(side);
    }

    @Override
    public List<Position> calculatePath(Position start, Position end) {
        int differenceX = end.x() - start.x();
        int differenceY = end.y() - start.y();
        validateMovingRule(differenceX, differenceY);
        return findPath(start, differenceX, differenceY);
    }

    private List<Position> findPath(Position start, int differenceX, int differenceY) {
        int differenceXAmount = Math.abs(differenceX);
        if (differenceX > 0) {
            return IntStream.range(1, differenceXAmount)
                    .mapToObj(start::right)
                    .toList();
        }
        if (differenceX < 0) {
            return IntStream.range(1, differenceXAmount)
                    .mapToObj(start::left)
                    .toList();
        }

        int differenceYAmount = Math.abs(differenceY);
        if (differenceY > 0) {
            return IntStream.range(1, differenceYAmount)
                    .mapToObj(start::up)
                    .toList();
        }
        return IntStream.range(1, differenceYAmount)
                .mapToObj(start::down)
                .toList();
    }

    private void validateMovingRule(int differenceX, int differenceY) {
        if ((differenceX == 0 && differenceY != 0)
                || (differenceX != 0 && differenceY == 0)) {
            return;
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}
