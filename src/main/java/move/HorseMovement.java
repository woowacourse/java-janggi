package move;


import static direction.Movement.DOWN;
import static direction.Movement.LEFT;
import static direction.Movement.RIGHT;
import static direction.Movement.UP;

import direction.Movement;
import direction.Point;
import java.util.List;
import java.util.Map;
import piece.Pieces;

public class HorseMovement implements MovementRule {

    private static final Map<Point, List<Movement>> paths = Map.of(
            new Point(-1, -2), List.of(UP),
            new Point(1, -2), List.of(UP),
            new Point(-2, -1), List.of(LEFT),
            new Point(-2, 1), List.of(LEFT),
            new Point(-1, 2), List.of(DOWN),
            new Point(1, 2), List.of(DOWN),
            new Point(2, -1), List.of(RIGHT),
            new Point(2, 1), List.of(RIGHT)
    );

    private final int direction;

    public HorseMovement(int direction) {
        this.direction = direction;
    }

    @Override
    public Point move(Pieces pieces, Point from, Point to) {
        List<Movement> movements = paths.getOrDefault(to.minus(from), List.of());
        validateInvalidDestination(movements);

        Point checkPoint = new Point(from.column(), from.row());
        validateExistPieceInPath(pieces, movements, checkPoint);

        return to;
    }

    private void validateInvalidDestination(List<Movement> movements) {
        if (movements.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }

    private void validateExistPieceInPath(Pieces pieces, List<Movement> movements, Point checkPoint) {
        if (checkExistPieceInPoint(pieces, movements, checkPoint)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
        }
    }

    private boolean checkExistPieceInPoint(Pieces pieces, List<Movement> movements, Point checkPoint) {
        return movements.stream()
                .anyMatch(movement -> pieces.isExistPieceIn(checkPoint.plus(movement.multiply(this.direction))));
    }
}
