package move;

import direction.Direction;
import direction.Point;
import java.util.ArrayList;
import java.util.List;
import piece.Pieces;

public class CannonMovement implements MovementRule {
    private static final String CANNON_EXPRESSION = "n";

    @Override
    public void validateDestination(Point from, Point to) {
        validateStraightDestination(from, to);
        validateNotSamePosition(from, to);
    }

    @Override
    public void checkPaths(Pieces allPieces, Point from, Point to) {
        if(calculateCannonPieceCountInPaths(allPieces, from, to) >= 1) {
            throw new IllegalArgumentException("[ERROR] 포가 존재하여 움직일 수 없습니다.");
        }
        if (calculateNotCannonPieceCountInPaths(allPieces, from, to) != 1) {
            throw new IllegalArgumentException("[ERROR] 포는 포를 제외한 하나의 기물을 넘어야 합니다.");
        }
    }

    private int calculateNotCannonPieceCountInPaths(Pieces pieces, Point from, Point to) {
        return (int) findPaths(from, to).stream()
                .filter(pieces::isContainPiece)
                .map(pieces::getByPoint)
                .filter(piece -> !piece.isSameType(CANNON_EXPRESSION))
                .count();
    }

    private int calculateCannonPieceCountInPaths(Pieces pieces, Point from, Point to) {
        return (int) findPaths(from, to).stream()
                .filter(pieces::isContainPiece)
                .map(pieces::getByPoint)
                .filter(piece -> piece.isSameType(CANNON_EXPRESSION))
                .count();
    }

    private List<Point> findPaths(Point from, Point to) {
        Direction direction = Direction.find(from, to);
        List<Point> paths = new ArrayList<>();
        Point current = new Point(from.x(), from.y());
        current = current.apply(direction);
        while(!current.equals(to)) {
            paths.add(current);
            current = current.apply(direction);
        }
        return paths;
    }

    private void validateStraightDestination(Point from, Point to) {
        if (from.x() != to.x() && from.y() != to.y()) {
            throw new IllegalArgumentException();
        }
    }

    private void validateNotSamePosition(Point from, Point to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException();
        }
    }
}
