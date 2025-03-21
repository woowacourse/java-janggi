package move;

import direction.Point;
import piece.Pieces;

public class GeneralMovement implements MovementRule {

    public static final int MAXIMUM_MOVE_RANGE = 1;

    @Override
    public Point move(Pieces pieces, Point from, Point to) {
        validatePossibleHorizontalMovement(from, to);
        validatePossibleVerticalMovement(from, to);

        return to;
    }

    private void validatePossibleHorizontalMovement(Point from, Point to) {
        if (from.x() + MAXIMUM_MOVE_RANGE < to.x() || from.x() - MAXIMUM_MOVE_RANGE > to.x()) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }

    private void validatePossibleVerticalMovement(Point from, Point to) {
        if (from.y() + MAXIMUM_MOVE_RANGE < to.y() || from.y() - MAXIMUM_MOVE_RANGE > to.y()) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }
}
