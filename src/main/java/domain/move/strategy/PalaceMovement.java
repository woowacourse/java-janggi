package domain.move.strategy;

import domain.board.Intersection;
import domain.move.Path;
import java.util.List;

public interface PalaceMovement {

    default List<Path> palaceDiagonalPaths(Intersection from) {
        if (from.isPalaceCenter()) {
            return createPathsToAllCorners(from);
        }

        if (from.isPalaceCorner()) {
            return List.of(
                    Path.of(toPalaceCenter(from))
            );
        }

        return List.of();
    }

    default List<Path> palaceDiagonalPathsForStraight(Intersection from) {
        if (from.isPalaceCenter()) {
            return createPathsToAllCorners(from);
        }

        if (from.isPalaceCorner()) {
            Intersection center = toPalaceCenter(from);

            return List.of(
                    Path.of(center),
                    new Path(nextDiagonal(center, from), List.of(center))
            );
        }

        return List.of();
    }

    private List<Path> createPathsToAllCorners(Intersection from) {
        if (!from.isPalaceCenter()) {
            throw new IllegalStateException("궁성의 모든 코너로 이동할 수 있는 곳은 중앙뿐입니다.");
        }

        final int row = from.row();
        final int file = from.file();

        return List.of(
                Path.of(new Intersection(row - 1, file - 1)),
                Path.of(new Intersection(row - 1, file + 1)),
                Path.of(new Intersection(row + 1, file - 1)),
                Path.of(new Intersection(row + 1, file + 1))
        );
    }

    private Intersection toPalaceCenter(Intersection from) {
        if (!from.isPalaceCorner()) {
            throw new IllegalStateException("궁성의 코너에서만 중앙으로 이동할 수 있습니다.");
        }

        if (from.row() == 1 || from.row() == 3) {
            return new Intersection(2, 5);
        }

        return new Intersection(9, 5);
    }

    private Intersection nextDiagonal(Intersection current, Intersection previous) {
        int deltaRow = current.row() - previous.row();
        int deltaFile = current.file() - previous.file();

        return new Intersection(current.row() + deltaRow, current.file() + deltaFile);
    }
}
