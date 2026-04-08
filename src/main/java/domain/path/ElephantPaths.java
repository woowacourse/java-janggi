package domain.path;

import domain.movement.Direction;
import domain.vo.Position;
import java.util.List;

public class ElephantPaths implements Paths {

    private static final List<Path> ELEPHANT_PATHS = List.of(
        new Path(List.of(Direction.UP, Direction.UP_LEFT, Direction.UP_LEFT)),
        new Path(List.of(Direction.UP, Direction.UP_RIGHT, Direction.UP_RIGHT)),
        new Path(List.of(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT)),
        new Path(List.of(Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT)),
        new Path(List.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_LEFT)),
        new Path(List.of(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT)),
        new Path(List.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP_LEFT)),
        new Path(List.of(Direction.LEFT, Direction.DOWN_LEFT, Direction.DOWN_LEFT))
    );

    @Override
    public List<Path> getPaths(Position position) {
        return ELEPHANT_PATHS;
    }
}
