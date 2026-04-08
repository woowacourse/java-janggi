package domain.movement;

import domain.board.Palace;
import domain.board.Position;
import java.util.List;

public class PalaceMovement implements Movement {
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
            paths = addPathIfReachable(paths, from, delta);
        }
        for (Delta delta : DIAGONAL_MOVES) {
            paths = addDiagonalPathIfOnPalaceLine(paths, from, delta);
        }
        return paths;
    }

    private Paths addPathIfReachable(Paths paths, Position from, Delta delta) {
        return Palace.of(from)
                .filter(palace -> from.canShift(delta) && palace.contains(from.shift(delta)))
                .map(palace -> paths.add(new Path(List.of(from.shift(delta)))))
                .orElse(paths);
    }

    private Paths addDiagonalPathIfOnPalaceLine(Paths paths, Position from, Delta delta) {
        return Palace.of(from)
                .filter(palace -> palace.isOnDiagonal(from))
                .filter(palace -> from.canShift(delta) && palace.isOnDiagonal(from.shift(delta)))
                .map(palace -> paths.add(new Path(List.of(from.shift(delta)))))
                .orElse(paths);
    }
}
