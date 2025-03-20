package move;


import static direction.Direction.DOWN;
import static direction.Direction.LEFT;
import static direction.Direction.RIGHT;
import static direction.Direction.UP;

import direction.Direction;
import direction.Point;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import piece.Pieces;

public class HorseMovement implements MovementRule {

    private static final Map<Point, List<Direction>> paths = Map.of(
            new Point(-1, -2), List.of(UP),
            new Point(1, -2), List.of(UP),
            new Point(-2, -1), List.of(LEFT),
            new Point(-2, 1), List.of(LEFT),
            new Point(-1, 2), List.of(DOWN),
            new Point(1, 2), List.of(DOWN),
            new Point(2, -1), List.of(RIGHT),
            new Point(2, 1), List.of(RIGHT)
    );

    private final int dir;

    public HorseMovement(int dir) {
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
