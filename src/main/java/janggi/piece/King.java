package janggi.piece;

import janggi.board.Position;
import java.util.List;
import java.util.Map;

public class King extends Piece {

    public King(final Side side) {
        super(side);
    }

    @Override
    public boolean canMove(Position start, Position end, Map<Position, Piece> board) {
        return isValidMovingRule(start, end);
    }

    private boolean isValidMovingRule(Position start, Position end) {
        int differenceX = end.x() - start.x();
        int differenceY = end.y() - start.y();
        int absDifferenceX = Math.abs(differenceX);
        int absDifferenceY = Math.abs(differenceY);
        return (absDifferenceX == 1 && absDifferenceY == 0) || (absDifferenceX == 0 && absDifferenceY == 1);
    }

    @Override
    public List<Position> calculatePath(final Position start, final Position end) {
        if (isValidMovingRule(start, end)) {
            return List.of();
        }
        throw new IllegalArgumentException("말의 이동 규칙과 어긋납니다.");
    }
}
