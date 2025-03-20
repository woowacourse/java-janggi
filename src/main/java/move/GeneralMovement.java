package move;

import direction.Point;
import piece.Pieces;

public class GeneralMovement implements MovementRule {

    private final int dir;

    public GeneralMovement(int dir) {
        this.dir = dir;
    }

    @Override
    public Point move(Pieces pieces, Point from, Point to) {
        if (from.x() + 1 < to.x() || from.x() - 1 > to.x()) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
        if (from.y() + 1 < to.y() || from.y() - 1 > to.y()) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }

        return to;
    }
}
