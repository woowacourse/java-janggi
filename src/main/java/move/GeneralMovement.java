package move;

import direction.Point;
import piece.Pieces;

public class GeneralMovement implements MovementRule {

    @Override
    public void validateDestination(Point from, Point to) {
        if (from.x() + 1 < to.x() || from.x() - 1 > to.x() || from.y() + 1 < to.y() || from.y() - 1 > to.y()) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }

    @Override
    public void checkPaths(Pieces allPieces, Point from, Point to) {

    }
}
