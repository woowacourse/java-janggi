package domain.rule;

import domain.direction.Direction;
import domain.position.Path;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class ListPathGenerator implements PathGenerator {

    private final List<List<Direction>> paths;

    public ListPathGenerator(List<List<Direction>> paths) {
        this.paths = paths;
    }

    @Override
    public Path calculatePath(Position src, Position dest) {
        for (List<Direction> path : paths) {
            try {
                List<Position> waypoints = new ArrayList<>();
                Position nextPosition = src;
                for (Direction direction : path) {
                    nextPosition = direction.move(nextPosition);
                    waypoints.add(nextPosition);
                }

                if (dest.equals(nextPosition)) {
                    waypoints.removeLast();
                    return new Path(src, dest, waypoints);
                }
            } catch (IllegalArgumentException e) {
            }
        }
        throw new IllegalArgumentException("목적지로 이동할 수 없습니다.");
    }
}
