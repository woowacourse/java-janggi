package domain.movement;

import domain.board.Position;
import java.util.List;

public final class HorseMovement implements Movement {

    private record HorseMove(Delta orthogonalDelta, Delta firstDiagonalDelta, Delta secondDiagonalDelta) {}

    private static final List<HorseMove> MOVES = List.of(
            new HorseMove(
                    new Delta(new ColDelta(0), new RowDelta(-1)),
                    new Delta(new ColDelta(-1), new RowDelta(-1)),
                    new Delta(new ColDelta(1), new RowDelta(-1))
            ),
            new HorseMove(
                    new Delta(new ColDelta(0), new RowDelta(1)),
                    new Delta(new ColDelta(-1), new RowDelta(1)),
                    new Delta(new ColDelta(1), new RowDelta(1))
            ),
            new HorseMove(
                    new Delta(new ColDelta(-1), new RowDelta(0)),
                    new Delta(new ColDelta(-1), new RowDelta(-1)),
                    new Delta(new ColDelta(-1), new RowDelta(1))
            ),
            new HorseMove(
                    new Delta(new ColDelta(1), new RowDelta(0)),
                    new Delta(new ColDelta(1), new RowDelta(-1)),
                    new Delta(new ColDelta(1), new RowDelta(1))
            )
    );

    @Override
    public Paths candidatePaths(Position from) {
        Paths paths = Paths.empty();
        for (HorseMove move : MOVES) {
            paths = addMovePaths(paths, from, move);
        }
        return paths;
    }

    private Paths addMovePaths(Paths paths, Position from, HorseMove move) {
        if (!from.canShift(move.orthogonalDelta())) {
            return paths;
        }
        Position blocking = from.shift(move.orthogonalDelta());
        paths = addDiagonalPath(paths, blocking, move.firstDiagonalDelta());
        return addDiagonalPath(paths, blocking, move.secondDiagonalDelta());
    }

    private Paths addDiagonalPath(Paths paths, Position blocking, Delta diagonalDelta) {
        if (!blocking.canShift(diagonalDelta)) {
            return paths;
        }
        Position destination = blocking.shift(diagonalDelta);
        return paths.add(new Path(List.of(blocking, destination)));
    }
}
