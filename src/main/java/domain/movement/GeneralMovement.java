package domain.movement;

import domain.game.Position;
import domain.vo.ColDelta;
import domain.vo.Delta;
import domain.vo.RowDelta;
import java.util.List;

public class GeneralMovement implements Movement {
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
            Position destination = from.shift(delta);
            paths = paths.add(new Path(List.of(destination)));
        }
        return paths;
    }
}