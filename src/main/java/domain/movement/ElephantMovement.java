package domain.movement;

import domain.game.Position;
import java.util.ArrayList;
import java.util.List;

public class ElephantMovement implements Movement {
    private static final int[][] MOVES = {
            {-1, 0, -1, -1, -1, 1},
            {1, 0, 1, -1, 1, 1},
            {0, -1, -1, -1, 1, -1},
            {0, 1, -1, 1, 1, 1}
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
            Position blocking = from.shift(orthCol, orthRow);

            for (int i = 2; i <= 4; i += 2) {
                int diagRow = move[i];
                int diagCol = move[i + 1];

                if (!blocking.canShift(diagCol, diagRow)) {
                    continue;
                }
                Position diagBlocking = blocking.shift(diagCol, diagRow);

                if (!diagBlocking.canShift(diagCol, diagRow)) {
                    continue;
                }
                Position destination = diagBlocking.shift(diagCol, diagRow);

                paths.add(new Path(List.of(blocking, diagBlocking, destination)));
            }
        }

        return paths;
    }
}
