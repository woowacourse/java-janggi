package move;

import direction.Point;
import java.util.ArrayList;
import java.util.List;
import piece.Pieces;

public class ChariotMovement implements MovementRule {

    @Override
    public Point move(Pieces pieces, Point from, Point to) {
        validateStraightDestination(from, to);

        List<Point> paths = findPaths(from, to);

        for (Point path : paths) {
            validateNonExistPieceInPath(pieces, path);
        }

        return new Point(to.column(), to.row());
    }

    private List<Point> findPaths(Point from, Point to) {
        List<Point> paths = new ArrayList<>();
        int minX = Math.min(from.column(), to.column());
        int maxX = Math.max(from.column(), to.column());
        int minY = Math.min(from.row(), to.row());
        int maxY = Math.max(from.row(), to.row());

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

    private static void validateNonExistPieceInPath(Pieces pieces, Point path) {
        if (pieces.isExistPieceIn(path)) {
            throw new IllegalArgumentException("[ERROR] 경로에 기물이 존재합니다.");
        }
    }
}
