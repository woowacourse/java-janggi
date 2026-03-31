package domain.place.palaceMoveStrategy;

import domain.position.Position;
import java.util.Set;

public class PalaceMovementRule {
    private static final Set<Position> NORMAL_PALACE = Set.of(
            new Position(1, 5),
            new Position(2, 4),
            new Position(2, 6),
            new Position(3, 5),
            new Position(8, 5),
            new Position(9, 4),
            new Position(9, 6),
            new Position(10, 5)
    );

    private static final Set<Position> SPECIAL_PALACE = Set.of(
            new Position(1, 4),
            new Position(1, 6),
            new Position(2, 5),
            new Position(3, 4),
            new Position(3, 6),
            new Position(8, 4),
            new Position(8, 6),
            new Position(9, 5),
            new Position(10, 4),
            new Position(10, 6)
    );

    public static boolean isInsidePalace(Position from) {
        return NORMAL_PALACE.contains(from) || SPECIAL_PALACE.contains(from);
    }

    public static boolean isInsideSpecialPalace(Position from) {
        return SPECIAL_PALACE.contains(from);
    }

}
