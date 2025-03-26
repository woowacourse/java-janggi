package piece;

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
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Elephant extends Piece {

    private static final Map<Point, List<Direction>> pathInfo = Map.of(
            new Point(-2, -3), List.of(UP, UP_LEFT_DIAGONAL),
            new Point(2, -3), List.of(UP, UP_RIGHT_DIAGONAL),
            new Point(-3, -2), List.of(LEFT, UP_LEFT_DIAGONAL),
            new Point(-3, 2), List.of(LEFT, DOWN_LEFT_DIAGONAL),
            new Point(-2, 3), List.of(DOWN, DOWN_LEFT_DIAGONAL),
            new Point(2, 3), List.of(DOWN, DOWN_RIGHT_DIAGONAL),
            new Point(3, -2), List.of(RIGHT, UP_RIGHT_DIAGONAL),
            new Point(3, 2), List.of(RIGHT, DOWN_RIGHT_DIAGONAL)
    );

    public Elephant(String name, Point point) {
        super(name, point);
    }

    @Override
    public void validateDestination(Point to) {
        validateAvailableDestination(currentPosition, to);
    }

    @Override
    public void checkPaths(Pieces allPieces, Point to) {
        Point checkPoint = new Point(currentPosition.x(), currentPosition.y());
        validateExistPieceInPath(allPieces, getPaths(currentPosition, to), checkPoint);
    }

    public List<Direction> getPaths(Point from, Point to) {
        Point distance = to.minus(from);
        return pathInfo.getOrDefault(distance, Collections.emptyList());
    }

    private void validateAvailableDestination(Point from, Point to) {
        if (getPaths(from, to).isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 선택할 수 없는 목적지입니다.");
        }
    }

    private void validateExistPieceInPath(Pieces pieces, List<Direction> paths, Point checkPoint) {
        if (isExistedPieceAtPoint(pieces, paths, checkPoint)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
        }
    }

    private boolean isExistedPieceAtPoint(Pieces pieces, List<Direction> paths, Point checkPoint) {
        return paths.stream()
                .anyMatch(direction -> pieces.isContainPiece(checkPoint.apply(direction)));
    }
}
