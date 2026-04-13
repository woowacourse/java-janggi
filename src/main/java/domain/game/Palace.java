package domain.game;

import domain.strategy.Direction;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Palace {
    private static final Set<Position> CHO = Set.of(
            Position.of(3, 0), Position.of(4, 0), Position.of(5, 0),
            Position.of(3, 1), Position.of(4, 1), Position.of(5, 1),
            Position.of(3, 2), Position.of(4, 2), Position.of(5, 2)
    );

    private static final Set<Position> HAN = Set.of(
            Position.of(3, 7), Position.of(4, 7), Position.of(5, 7),
            Position.of(3, 8), Position.of(4, 8), Position.of(5, 8),
            Position.of(3, 9), Position.of(4, 9), Position.of(5, 9)
    );

    private static final Map<Side, Set<Position>> OWN = Map.of(
            Side.CHO, CHO,
            Side.HAN, HAN
    );

    private static final Map<Position, List<Direction>> DIAGONALS = Map.of(
            Position.of(3, 0), List.of(Direction.NE),
            Position.of(5, 0), List.of(Direction.NW),
            Position.of(4, 1), List.of(Direction.NE, Direction.NW, Direction.SE, Direction.SW),
            Position.of(3, 2), List.of(Direction.SE),
            Position.of(5, 2), List.of(Direction.SW),
            Position.of(3, 9), List.of(Direction.SE),
            Position.of(5, 9), List.of(Direction.SW),
            Position.of(4, 8), List.of(Direction.NE, Direction.NW, Direction.SE, Direction.SW),
            Position.of(3, 7), List.of(Direction.NE),
            Position.of(5, 7), List.of(Direction.NW)
    );

    private Palace() {
    }

    public static boolean isPalace(Position position) {
        return CHO.contains(position) || HAN.contains(position);
    }

    public static boolean isOwnPalace(Side side, Position position) {
        return OWN.get(side).contains(position);
    }

    public static List<Direction> diagonals(Position position) {
        return DIAGONALS.getOrDefault(position, List.of());
    }

    public static boolean canMoveDiagonal(Position from, Direction direction) {
        if (!diagonals(from).contains(direction)) {
            return false;
        }
        if (!from.canMove(direction)) {
            return false;
        }
        return isPalace(from.move(direction));
    }
}
