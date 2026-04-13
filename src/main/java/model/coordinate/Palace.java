package model.coordinate;

import java.util.Set;
import model.Team;

public class Palace {

    private static final Set<Position> HAN_PALACE = Set.of(
            new Position(0, 3), new Position(0, 4), new Position(0, 5),
            new Position(1, 3), new Position(1, 4), new Position(1, 5),
            new Position(2, 3), new Position(2, 4), new Position(2, 5)
    );

    private static final Set<Position> CHO_PALACE = Set.of(
            new Position(7, 3), new Position(7, 4), new Position(7, 5),
            new Position(8, 3), new Position(8, 4), new Position(8, 5),
            new Position(9, 3), new Position(9, 4), new Position(9, 5)
    );

    private static final Set<Position> HAN_DIAGONAL_POINTS = Set.of(
            new Position(0, 3), new Position(0, 5),
            new Position(1, 4),
            new Position(2, 3), new Position(2, 5)
    );

    private static final Set<Position> CHO_DIAGONAL_POINTS = Set.of(
            new Position(7, 3), new Position(7, 5),
            new Position(8, 4),
            new Position(9, 3), new Position(9, 5)
    );

    private Palace() {
    }

    public static boolean contains(Team team, Position position) {
        if (team.isHan()) {
            return HAN_PALACE.contains(position);
        }
        return CHO_PALACE.contains(position);
    }

    public static boolean isDiagonalPoint(Team team, Position position) {
        if (team.isHan()) {
            return HAN_DIAGONAL_POINTS.contains(position);
        }
        return CHO_DIAGONAL_POINTS.contains(position);
    }
}