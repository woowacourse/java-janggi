package move;

import direction.Point;
import java.util.ArrayList;
import java.util.List;
import piece.Piece;
import piece.Pieces;

public class ChariotMovement implements MovementRule {

    @Override
    public Point move(Pieces pieces, Point from, Point to) {
        validateStraightDestination(from, to);
        validateStartSameDestination(from, to);

        List<Point> paths = findPaths(from, to);

        for (Point path : paths) {
            Piece piece = pieces.findByPoint(path);
            validateNonExistPieceInPath(piece);
        }

        return new Point(to.x(), to.y());
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
        if(from.x() != to.x() && from.y() != to.y()) {
            throw new IllegalArgumentException();
        }
    }

    private void validateStartSameDestination(Point from, Point to) {
        if(from.equals(to)) {
            throw new IllegalArgumentException();
        }
    }

    private void validateNonExistPieceInPath(final Piece piece) {
        if (piece != null) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
        }
    }
}
