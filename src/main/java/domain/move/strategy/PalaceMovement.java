package domain.move.strategy;

import domain.board.Intersection;
import domain.move.Path;
import java.util.List;

public interface PalaceMovement {

    default Intersection toPalaceCenter(Intersection from) {
        if (!from.isPalaceCorner()) {
            throw new IllegalStateException("궁성의 코너에서만 중앙으로 이동할 수 있습니다.");
        }

        if (from.row() == 1 || from.row() == 3) {
            return new Intersection(2, 5);
        }

        return new Intersection(9, 5);
    }

    default List<Path> palaceDiagonalPaths(Intersection from) {
        if (from.isPalaceCenter()) {
            final int row = from.row();
            final int file = from.file();

            return List.of(
                    Path.of(new Intersection(row - 1, file - 1)),
                    Path.of(new Intersection(row - 1, file + 1)),
                    Path.of(new Intersection(row + 1, file - 1)),
                    Path.of(new Intersection(row + 1, file + 1))
            );
        }

        if (from.isPalaceCorner()) {
            return List.of(
                    Path.of(toPalaceCenter(from))
            );
        }

        return List.of();
    }
}
