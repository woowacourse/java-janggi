package domain.movement;

import domain.game.Position;
import domain.vo.Team;
import java.util.ArrayList;
import java.util.List;

public class SoldierMovement implements Movement {
    private static final int[][] MOVES_HAN = {
            {1, 0},
            {0, -1},
            {0, 1}
    };
    private static final int[][] MOVES_CHO = {
            {-1, 0},
            {0, -1},
            {0, 1}
    };

    private final Team team;

    public SoldierMovement(Team team) {
        this.team = team;
    }

    @Override
    public List<Path> candidatePaths(Position from) {
        List<Path> paths = new ArrayList<>();

        int[][] teamMoves = team == Team.HAN ? MOVES_HAN : MOVES_CHO;

        for (int[] move : teamMoves) {
            int orthRow = move[0];
            int orthCol = move[1];

            if (!from.canShift(orthCol, orthRow)) {
                continue;
            }

            Position destination = from.shift(orthCol, orthRow);

            List<Position> path = new ArrayList<>();
            path.add(destination);
            paths.add(new Path(path));
        }
        return paths;
    }
}
