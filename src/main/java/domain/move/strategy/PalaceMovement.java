package domain.move.strategy;

import domain.board.Intersection;
import domain.board.Palace;
import domain.move.Path;
import java.util.List;

public interface PalaceMovement {

    default List<Path> palaceDiagonalPaths(Intersection from) {
        if (Palace.isCenter(from)) {
            return Palace.getCornersOf(from).stream()
                    .map(Path::of)
                    .toList();
        }

        if (Palace.isCorner(from)) {
            return List.of(Path.of(Palace.getCenterOf(from)));
        }

        return List.of();
    }

    default List<Path> palaceDiagonalPathsForStraight(Intersection from) {
        if (Palace.isCenter(from)) {
            return palaceDiagonalPaths(from);
        }

        if (Palace.isCorner(from)) {
            Intersection center = toPalaceCenter(from);
            Intersection opposite = Palace.getOppositeCornerOf(from);

            return List.of(
                    Path.of(center),
                    new Path(opposite, List.of(center))
            );
        }

        return List.of();
    }

    private Intersection toPalaceCenter(Intersection from) {
        return Palace.getCenterOf(from);
    }
}
