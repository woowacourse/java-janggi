package move;

import static direction.Direction.DOWN;
import static direction.Direction.DOWN_LEFT_DIAGONAL;
import static direction.Direction.DOWN_RIGHT_DIAGONAL;
import static direction.Direction.LEFT;
import static direction.Direction.RIGHT;
import static direction.Direction.UP;
import static direction.Direction.UP_LEFT_DIAGONAL;
import static direction.Direction.UP_RIGHT_DIAGONAL;

import direction.Direction;
import direction.Point;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import piece.Pieces;

public class ElephantMovement implements MovementRule {

    private static final Map<Point, List<Direction>> paths = Map.of(
            new Point(-2, -3), List.of(UP, UP_LEFT_DIAGONAL),
            new Point(2, -3), List.of(UP, UP_RIGHT_DIAGONAL),
            new Point(-3, -2), List.of(LEFT, UP_LEFT_DIAGONAL),
            new Point(-3, 2), List.of(LEFT, DOWN_LEFT_DIAGONAL),
            new Point(-2, 3), List.of(DOWN, DOWN_LEFT_DIAGONAL),
            new Point(2, 3), List.of(DOWN, DOWN_RIGHT_DIAGONAL),
            new Point(3, -2), List.of(RIGHT, UP_RIGHT_DIAGONAL),
            new Point(3, 2), List.of(RIGHT, DOWN_RIGHT_DIAGONAL)
    );

    private final int dir;

    public ElephantMovement(int dir) {
        this.dir = dir;
    }

    @Override
    public Point move(Pieces pieces, Point from, Point to) {
        List<Direction> directions = paths.get(to.minus(from));
        if(directions == null) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
        Point checkPoint = new Point(from.x(), from.y());
        directions.stream()
                .map(direction -> pieces.findByPoint(checkPoint.plus(direction.multiply(dir))))
                .filter(Optional::isEmpty)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다."));
        return to;
    }
}
