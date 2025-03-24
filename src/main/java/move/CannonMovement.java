package move;

import direction.Point;
import java.util.ArrayList;
import java.util.List;
import piece.Piece;
import piece.Pieces;

public class CannonMovement implements MovementRule {
    private static final String CANNON_EXPRESSION = "n";
    private static final int CANNON_MOVEABLE_PIECE_COUNT = 1;

    @Override
    public Point move(Pieces pieces, Point from, Point to) {
        validateStraightDestination(from, to);

        List<Point> paths = findPaths(from, to);

        int count = countPiecesInPaths(pieces, paths);
        validateCannonMoveable(count);

        return new Point(to.column(), to.row());
    }

    private int countPiecesInPaths(Pieces pieces, List<Point> paths) {
        int count = 0;
        for (Point path : paths) {
            count += countExistNonCannonPiece(pieces, path);
        }

        return count;
    }

    private int countExistNonCannonPiece(Pieces pieces, Point path) {
        if (pieces.isExistPieceIn(path)) {
            Piece piece = pieces.findByPoint(path);
            validateIsNotCannon(piece);
            return 1;
        }

        return 0;
    }

    private void validateCannonMoveable(int count) {
        if (count != CANNON_MOVEABLE_PIECE_COUNT) {
            throw new IllegalArgumentException("[ERROR] 포는 기물을 하나만 넘을 수 있습니다.");
        }
    }

    private List<Point> findPaths(Point from, Point to) {
        int minX = Math.min(from.column(), to.column());
        int maxX = Math.max(from.column(), to.column());
        int minY = Math.min(from.row(), to.row());
        int maxY = Math.max(from.row(), to.row());

        List<Point> paths = new ArrayList<>();
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                if (from.equals(new Point(x, y))) {
                    continue;
                }

                paths.add(new Point(x, y));
            }
        }
        return paths;
    }

    private void validateStraightDestination(Point from, Point to) {
        if (from.isDifferentColumn(to) && from.isDifferentRow(to)) {
            throw new IllegalArgumentException("[ERROR] 움직일 수 없는 경로입니다.");
        }
    }

    private void validateIsNotCannon(Piece piece) {
        if (piece.isSameType(CANNON_EXPRESSION)) {
            throw new IllegalArgumentException("[ERROR] 포가 존재하여 움직일 수 없습니다.");
        }
    }
}
