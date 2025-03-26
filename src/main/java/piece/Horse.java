package piece;

import static direction.Direction.DOWN;
import static direction.Direction.LEFT;
import static direction.Direction.RIGHT;
import static direction.Direction.UP;

import direction.Direction;
import direction.Point;
import java.util.List;
import java.util.Map;

public class Horse extends Piece {

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

    public Horse(String name, Point point) {
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

    private List<Direction> getPaths(Point from, Point to) {
        return paths.getOrDefault(to.minus(from), List.of());
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
