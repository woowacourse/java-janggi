package domain.movement;

import domain.game.Position;
import domain.vo.ColDelta;
import domain.vo.Delta;
import domain.vo.RowDelta;
import java.util.ArrayList;
import java.util.List;

public class StepPieceMovement implements Movement {
    private static final List<Delta> MOVES = List.of(
            new Delta(new ColDelta(0), new RowDelta(-1)),
            new Delta(new ColDelta(0), new RowDelta(1)),
            new Delta(new ColDelta(-1), new RowDelta(0)),
            new Delta(new ColDelta(1), new RowDelta(0))
    );

    @Override
    public Paths candidatePaths(Position from) {
        Paths paths = Paths.empty();

        for (Delta delta : MOVES) {
            if (!from.canShift(delta)) {
                continue;
            }

            List<Position> path = new ArrayList<>();
            Position nextPosition = from;
            while (nextPosition.canShift(delta)) {
                nextPosition = nextPosition.shift(delta);
                path.add(nextPosition);
            }

            paths = paths.add(new Path(path));
        }
        return paths;
    }
}