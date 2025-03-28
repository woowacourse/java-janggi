package domain.position;

import domain.TeamType;
import java.util.Arrays;
import java.util.List;

public enum Palace {
    HAN_PALACE(TeamType.HAN,
            List.of(
                    Position.of(0, 3),
                    Position.of(0, 4),
                    Position.of(0, 5),
                    Position.of(1, 3),
                    Position.of(1, 4),
                    Position.of(1, 5),
                    Position.of(2, 3),
                    Position.of(2, 4),
                    Position.of(2, 5)
            ),
            Position.of(1, 4)
    ),
    CHO_PALACE(TeamType.CHO,
            List.of(
                    Position.of(9, 3),
                    Position.of(9, 4),
                    Position.of(9, 5),
                    Position.of(8, 3),
                    Position.of(8, 4),
                    Position.of(8, 5),
                    Position.of(7, 3),
                    Position.of(7, 4),
                    Position.of(7, 5)
            ),
            Position.of(8, 4)
    );

    private final TeamType team;
    private final List<Position> palacePositions;
    private final Position crossMovablePosition;

    Palace(TeamType team, List<Position> palacePositions, Position crossMovablePosition) {
        this.team = team;
        this.palacePositions = palacePositions;
        this.crossMovablePosition = crossMovablePosition;
    }

    public static boolean isInPalace(Position position) {
        return Arrays.stream(values())
                .flatMap(palace -> palace.palacePositions.stream())
                .anyMatch(palacePosition -> palacePosition.equals(position));
    }

    public static boolean isValidDirection(Position from, Direction direction) {
        boolean isNormalDirection = !direction.isCrossDirection();
        return isNormalDirection || isValidCrossDirection(from, direction);
    }

    private static boolean isValidCrossDirection(Position from, Direction direction) {
        if (isCrossMovablePosition(from)) {
            return true;
        }
        Position to = from.moveDirection(direction);
        return isCrossMovablePosition(to);
    }

    private static boolean isCrossMovablePosition(Position to) {
        return Arrays.stream(values())
                .map(palace -> palace.crossMovablePosition)
                .anyMatch(palacePosition -> palacePosition.equals(to));
    }
}
