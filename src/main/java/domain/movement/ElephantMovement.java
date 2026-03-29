package domain.movement;

import domain.game.Position;
import domain.vo.ColDelta;
import domain.vo.Delta;
import domain.vo.RowDelta;
import java.util.List;

public class ElephantMovement implements Movement {

    private record ElephantMove(Delta orthogonalDelta, Delta firstDiagonalDelta, Delta secondDiagonalDelta) {}

    private static final List<ElephantMove> MOVES = List.of(
            new ElephantMove(
                    new Delta(new ColDelta(0), new RowDelta(-1)),
                    new Delta(new ColDelta(-1), new RowDelta(-1)),
                    new Delta(new ColDelta(1), new RowDelta(-1))
            ),
            new ElephantMove(
                    new Delta(new ColDelta(0), new RowDelta(1)),
                    new Delta(new ColDelta(-1), new RowDelta(1)),
                    new Delta(new ColDelta(1), new RowDelta(1))
            ),
            new ElephantMove(
                    new Delta(new ColDelta(-1), new RowDelta(0)),
                    new Delta(new ColDelta(-1), new RowDelta(-1)),
                    new Delta(new ColDelta(-1), new RowDelta(1))
            ),
            new ElephantMove(
                    new Delta(new ColDelta(1), new RowDelta(0)),
                    new Delta(new ColDelta(1), new RowDelta(-1)),
                    new Delta(new ColDelta(1), new RowDelta(1))
            )
    );

    @Override
    public Paths candidatePaths(Position from) {
        Paths paths = Paths.empty();
        for (ElephantMove move : MOVES) {
            paths = addMovePaths(paths, from, move);
        }
        return paths;
    }

    private Paths addMovePaths(Paths paths, Position from, ElephantMove move) {
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
        Position diagBlocking = blocking.shift(diagonalDelta);
        if (!diagBlocking.canShift(diagonalDelta)) {
            return paths;
        }
        Position destination = diagBlocking.shift(diagonalDelta);
        return paths.add(new Path(List.of(blocking, diagBlocking, destination)));
    }
}
