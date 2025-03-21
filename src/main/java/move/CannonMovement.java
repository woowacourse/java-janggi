package move;

import direction.Point;
import java.util.ArrayList;
import java.util.List;
import piece.Piece;
import piece.Pieces;

public class CannonMovement implements MovementRule {
    private static final String CANNON_EXISTED = "[ERROR] 포가 존재하여 움직일 수 없습니다.";
    private static final String CANNON_EXPRESSION = "n";

    @Override
    public Point move(Pieces pieces, Point from, Point to) {
        validateStraightDestination(from, to);
        validateStartSameDestination(from, to);

        List<Point> paths = findPaths(from, to);

        int count = 0;
        for (Point path : paths) {
            if (pieces.isExistPieceIn(path)) {
                Piece piece = pieces.findByPoint(path);
                validateIsNotCannon(piece);
                count++;
            }
        }

        if (count == 1) {
            return new Point(to.x(), to.y());
        }

        throw new IllegalArgumentException("[ERROR] 포는 기물을 하나만 넘을 수 있습니다.");
    }

    private List<Point> findPaths(Point from, Point to) {
        List<Point> paths = new ArrayList<>();
        int minX = Math.min(from.x(), to.x());
        int maxX = Math.max(from.x(), to.x());
        int minY = Math.min(from.y(), to.y());
        int maxY = Math.max(from.y(), to.y());

        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                if (x == minX && y == minY) {
                    continue;
                }

                paths.add(new Point(x, y));
            }
        }
        return paths;
    }

    private void validateStraightDestination(Point from, Point to) {
        if (from.x() != to.x() && from.y() != to.y()) {
            throw new IllegalArgumentException();
        }
    }

    private void validateStartSameDestination(Point from, Point to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateIsNotCannon(Piece piece) {
        if (piece.isSameType(CANNON_EXPRESSION)) {
            throw new IllegalArgumentException(CANNON_EXISTED);
        }
    }
}
