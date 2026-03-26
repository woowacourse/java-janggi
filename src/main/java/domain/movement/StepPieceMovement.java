package domain.movement;

import domain.game.Position;
import java.util.ArrayList;
import java.util.List;

public class StepPieceMovement implements Movement {
    private static final int[][] MOVES = {
            {-1, 0},
            {1, 0},
            {0, -1},
            {0, 1}
    };

    @Override
    public List<Path> candidatePaths(Position from) {
        List<Path> paths = new ArrayList<>();

        for (int[] move : MOVES) {
            int orthRow = move[0];
            int orthCol = move[1];

            if (!from.canShift(orthCol, orthRow)) {
                continue;
            }

            List<Position> path = new ArrayList<>();
            // 보드 범위가 허용안될때까지

            Position nextPosition = from;
            while (nextPosition.canShift(orthCol, orthRow)) {
                nextPosition = nextPosition.shift(orthCol, orthRow);
                path.add(nextPosition);
            }

            paths.add(new Path(path));
        }
        return paths;
    }
}
