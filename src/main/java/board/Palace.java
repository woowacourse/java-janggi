package board;

import position.Position;

import java.util.Set;

public class Palace {

    private static final Set<Position> centerPositions;

    static {
        centerPositions = Set.of(
                new Position(5, 2),
                new Position(5, 9)
        );
    }

    public static Set<Position> getCenterPositions() {
        return centerPositions;
    }
}
