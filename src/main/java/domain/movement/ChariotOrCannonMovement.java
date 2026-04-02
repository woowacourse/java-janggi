package domain.movement;

import domain.board.Position;
import java.util.ArrayList;
import java.util.List;

public class ChariotOrCannonMovement implements Movement {
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
            paths = addLinePath(paths, from, delta);
        }
        return paths;
    }

    private Paths addLinePath(Paths paths, Position from, Delta delta) {
        if (!from.canShift(delta)) {
            return paths;
        }
        return paths.add(new Path(buildLine(from, delta)));
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
