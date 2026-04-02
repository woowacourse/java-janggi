package domain.movement;

import domain.board.Palace;
import domain.board.Position;
import java.util.ArrayList;
import java.util.List;

public class LinearMovement implements Movement {
    private static final List<Delta> ORTHOGONAL_MOVES = List.of(
            new Delta(new ColDelta(0), new RowDelta(-1)),
            new Delta(new ColDelta(0), new RowDelta(1)),
            new Delta(new ColDelta(-1), new RowDelta(0)),
            new Delta(new ColDelta(1), new RowDelta(0))
    );
    private static final List<Delta> DIAGONAL_MOVES = List.of(
            new Delta(new ColDelta(-1), new RowDelta(-1)),
            new Delta(new ColDelta(1), new RowDelta(-1)),
            new Delta(new ColDelta(-1), new RowDelta(1)),
            new Delta(new ColDelta(1), new RowDelta(1))
    );

    @Override
    public Paths candidatePaths(Position from) {
        Paths paths = Paths.empty();
        for (Delta delta : ORTHOGONAL_MOVES) {
            paths = addLinePath(paths, from, delta);
        }
        for (Delta delta : DIAGONAL_MOVES) {
            paths = addDiagonalLinePath(paths, from, delta);
        }
        return paths;
    }

    private Paths addLinePath(Paths paths, Position from, Delta delta) {
        if (!from.canShift(delta)) {
            return paths;
        }
        return paths.add(new Path(buildLine(from, delta)));
    }

    private Paths addDiagonalLinePath(Paths paths, Position from, Delta delta) {
        return Palace.of(from)
                .filter(palace -> palace.isOnDiagonal(from) && from.canShift(delta))
                .map(palace -> paths.add(new Path(buildLine(from, delta))))
                .orElse(paths);
    }

    private List<Position> buildLine(Position from, Delta delta) {
        List<Position> path = new ArrayList<>();
        Position nextPosition = from;
        while (nextPosition.canShift(delta)) {
            nextPosition = nextPosition.shift(delta);
            path.add(nextPosition);
        }
        return path;
    }
}
