package location;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PathUtility {
    public static void checkStraightMovement(Position from, Position to) {
        if (from.x() != to.x() && from.y() != to.y()) {
            throw new IllegalArgumentException("[ERROR] 직선 이동만 가능합니다.");
        }
    }

    public static void checkOneMovement(Position from, Position to) {
        if (from.x() + 1 < to.x() || from.x() - 1 > to.x()
                || from.y() + 1 < to.y() || from.y() - 1 > to.y()) {
            throw new IllegalArgumentException("[ERROR] 1칸만 이동 가능합니다.");
        }
    }

    public static void checkNotSameStartWithEnd(Position from, Position to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 출발지와 목적지는 달라야 합니다.");
        }
    }

    public static List<Position> calculateStraightPaths(Position from, Position to) {
        Direction direction = Direction.find(from, to);
        List<Position> paths = new ArrayList<>();
        Position current = from.apply(direction);

        while (!current.equals(to)) {
            paths.add(current);
            current = current.apply(direction);
        }
        return paths;
    }
}
