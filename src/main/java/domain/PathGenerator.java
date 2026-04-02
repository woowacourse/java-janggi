package domain;

import java.util.ArrayList;
import java.util.List;

public class PathGenerator {
    public static List<Position> generateStraightPath(Position departure, Position destination, Direction direction) {
        List<Position> paths = new ArrayList<>();

        Position current = departure;
        while (!current.equals(destination)) {
            current = current.move(direction.getDeltaX(), direction.getDeltaY());
            paths.add(current);
        }

        return paths;
    }
}
