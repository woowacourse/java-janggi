package janggi.domain.piece.move.strategy;

import janggi.domain.board.Direction;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Palace;
import janggi.domain.board.Point;
import janggi.domain.piece.Piece;
import janggi.domain.piece.move.MoveStrategy;
import janggi.domain.piece.move.Path;
import janggi.domain.piece.move.PathCalculator;
import java.util.List;
import java.util.Set;

public class PalaceAreaStrategy implements MoveStrategy {

    private static final Set<Point> mustPassedDiagonalPoint = Set.of(
            new Point(2, 5),
            new Point(9, 5)
    );

    private final PathCalculator pathCalculator;

    public PalaceAreaStrategy(PathCalculator pathCalculator) {
        this.pathCalculator = pathCalculator;
    }

    @Override
    public boolean isMovable(JanggiBoard janggiBoard, Piece piece, Point start, Point end) {
        if (!Palace.isInPalace(start, end)) {
            return false;
        }
        Path path = pathCalculator.calculate(start, end);
        return !isAvailableDiagonal(start, end, path);
    }

    private boolean isAvailableDiagonal(Point start, Point end, Path path) {
        List<Direction> pathDirections = path.getPath();
        List<Point> movedPoints = path.getMovedPoints(start, end);
        for (int i = 1; i < movedPoints.size(); i++) {
            Direction direction = pathDirections.get(i - 1);
            if (isPassCenter(direction, movedPoints, i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isPassCenter(Direction direction, List<Point> movedPoints, int i) {
        if (direction.isDiagonal()) {
            Point before = movedPoints.get(i - 1);
            Point current = movedPoints.get(i);
            if (!mustPassedDiagonalPoint.contains(before) && !mustPassedDiagonalPoint.contains(current)) {
                return true;
            }
        }
        return false;
    }
}
