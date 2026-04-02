package domain.movement;

import domain.board.Position;
import java.util.List;

public class GuardMovement implements Movement {
    private static final List<Delta> MOVES = List.of(
            new Delta(new ColumnDelta(0), new RowDelta(-1)),
            new Delta(new ColumnDelta(0), new RowDelta(1)),
            new Delta(new ColumnDelta(-1), new RowDelta(0)),
            new Delta(new ColumnDelta(1), new RowDelta(0))
    );

    @Override
    public Paths candidatePaths(Position from) {
        Paths paths = Paths.empty();
        for (Delta delta : MOVES) {
            paths = addPathIfReachable(paths, from, delta);
        }
        return paths;
    }

    private Paths addPathIfReachable(Paths paths, Position from, Delta delta) {
        if (!from.canShift(delta)) {
            return paths;
        }
        return paths.add(new Path(List.of(from.shift(delta))));
    }
}
