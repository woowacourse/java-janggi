package domain.movement;

import domain.game.Position;
import java.util.ArrayList;
import java.util.List;

public class GuardMovement implements Movement {
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
            // 보드 밖으로 나갈 일은 없음.
            Position destination = from.shift(orthCol, orthRow);
            // 데스티네이션: 목적지
            List<Position> path = new ArrayList<>();
            path.add(destination);
            paths.add(new Path(path));
        }
        return paths;
    }
}
