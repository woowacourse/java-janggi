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

    private final int direction;

    public ElephantMovement(int direction) {
        this.direction = direction;
    }

    @Override
    public Point move(Pieces pieces, Point from, Point to) {
        List<Direction> directions = paths.getOrDefault(to.minus(from), List.of());
        validateInvalidDestination(directions);

        Point checkPoint = new Point(from.x(), from.y());
        validateExistPieceInPath(pieces, directions, checkPoint);

        return to;
    }

    private void validateInvalidDestination(List<Direction> directions) {
        if(directions.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }

    private void validateExistPieceInPath(Pieces pieces, List<Direction> directions, Point checkPoint) {
        if (checkExistPieceInPoint(pieces, directions, checkPoint)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
        }
    }

    private boolean checkExistPieceInPoint(Pieces pieces, List<Direction> directions, Point checkPoint) {
        return directions.stream()
                .anyMatch(direction -> pieces.isExistPieceIn(checkPoint.plus(direction.multiply(this.direction))));
    }
}
