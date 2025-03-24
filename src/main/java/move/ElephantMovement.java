package move;

import static direction.Movement.DOWN;
import static direction.Movement.DOWN_LEFT_DIAGONAL;
import static direction.Movement.DOWN_RIGHT_DIAGONAL;
import static direction.Movement.LEFT;
import static direction.Movement.RIGHT;
import static direction.Movement.UP;
import static direction.Movement.UP_LEFT_DIAGONAL;
import static direction.Movement.UP_RIGHT_DIAGONAL;

import direction.Movement;
import direction.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import piece.Pieces;

public class ElephantMovement implements MovementRule {

    private static final Map<Point, List<Movement>> paths = Map.of(
            new Point(-2, -3), List.of(UP, UP_LEFT_DIAGONAL),
            new Point(2, -3), List.of(UP, UP_RIGHT_DIAGONAL),
            new Point(-3, -2), List.of(LEFT, UP_LEFT_DIAGONAL),
            new Point(-3, 2), List.of(LEFT, DOWN_LEFT_DIAGONAL),
            new Point(-2, 3), List.of(DOWN, DOWN_LEFT_DIAGONAL),
            new Point(2, 3), List.of(DOWN, DOWN_RIGHT_DIAGONAL),
            new Point(3, -2), List.of(RIGHT, UP_RIGHT_DIAGONAL),
            new Point(3, 2), List.of(RIGHT, DOWN_RIGHT_DIAGONAL)
    );

    private final int direction;

    public ElephantMovement(int direction) {
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

    private void validateExistPieceInPath(Pieces pieces, List<Movement> movements, Point startPoint) {
        List<Point> path = directionsToPath(movements, startPoint);

        for (Point point : path) {
            validateExistPieceInPoint(pieces, point);
        }
    }

    private List<Point> directionsToPath(List<Movement> movements, Point checkPoint) {
        List<Point> path = new ArrayList<>();
        for (Movement movement : movements) {
            Point nextDirection = movement.multiply(this.direction);
            checkPoint = checkPoint.plus(nextDirection);

            path.add(checkPoint);
        }

        return path;
    }

    private void validateExistPieceInPoint(Pieces pieces, Point point) {
        if (pieces.isExistPieceIn(point)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
        }
    }
}
