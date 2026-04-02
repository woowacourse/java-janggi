package janggi.strategy;

import janggi.domain.board.Position;
import janggi.domain.route.Path;
import janggi.domain.route.Paths;
import java.util.ArrayList;
import java.util.List;

public class MoveStrategyTestHelper {
    public static List<Position> toList(Path path) {
        List<Position> list = new ArrayList<>();
        path.forEach(list::add);
        return list;
    }

    public static Paths createRoute(List<Position> positions) {
        Path path = new Path();
        positions.forEach(path::add);
        Paths paths = new Paths();
        paths.addPath(path);
        return paths;
    }
}
