package janggi.domain.moveRule.pathStrategy;

import janggi.domain.board.Direction;
import janggi.domain.board.Movement;
import java.util.List;

public class ElephantPathStrategy extends FixedPathStrategy {
    private final static PathStrategy INSTANCE = new ElephantPathStrategy();

    ElephantPathStrategy() {
        super(List.of(
                Movement.from(Direction.UP, Direction.UP_LEFT, Direction.UP_LEFT),
                Movement.from(Direction.UP, Direction.UP_RIGHT, Direction.UP_RIGHT),
                Movement.from(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_LEFT),
                Movement.from(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT),
                Movement.from(Direction.LEFT, Direction.UP_LEFT, Direction.UP_LEFT),
                Movement.from(Direction.LEFT, Direction.DOWN_LEFT, Direction.DOWN_LEFT),
                Movement.from(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT),
                Movement.from(Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT)
        ));
    }

    public static PathStrategy getInstance() {
        return INSTANCE;
    }

}
