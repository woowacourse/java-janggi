package move;

import static direction.Direction.LEFT;
import static direction.Direction.RIGHT;
import static direction.Direction.UP;

import direction.Direction;
import direction.Point;
import java.util.List;
import piece.Pieces;

public class SoldierMovement implements MovementRule {

    private static final List<Direction> paths = List.of(LEFT, RIGHT, UP);

    private final int dir;

    public SoldierMovement(int dir) {
        this.dir = dir;
    }

    @Override
    public Point move(Pieces pieces, Point from, Point to) {
        for (Direction direction : paths) {
            if (from.plus(direction.multiply(dir)).equals(to)) {
                return to;
            }
        }

        throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
    }
}
