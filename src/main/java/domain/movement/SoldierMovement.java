package domain.movement;

import domain.game.Position;
import domain.vo.ColDelta;
import domain.vo.Delta;
import domain.vo.RowDelta;
import domain.vo.Team;
import java.util.List;

public class SoldierMovement implements Movement {
    private static final List<Delta> MOVES_HAN = List.of(
            new Delta(new ColDelta(0), new RowDelta(1)),
            new Delta(new ColDelta(-1), new RowDelta(0)),
            new Delta(new ColDelta(1), new RowDelta(0))
    );
    private static final List<Delta> MOVES_CHO = List.of(
            new Delta(new ColDelta(0), new RowDelta(-1)),
            new Delta(new ColDelta(-1), new RowDelta(0)),
            new Delta(new ColDelta(1), new RowDelta(0))
    );

    private final Team team;

    public SoldierMovement(Team team) {
        this.team = team;
    }

    @Override
    public Paths candidatePaths(Position from) {
        Paths paths = Paths.empty();
        for (Delta delta : teamMoves()) {
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

    private List<Delta> teamMoves() {
        if (team == Team.HAN) {
            return MOVES_HAN;
        }
        return MOVES_CHO;
    }
}
