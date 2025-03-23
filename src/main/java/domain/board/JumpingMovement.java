package domain.board;

import static domain.board.Direction.DOWN;
import static domain.board.Direction.LEFT;
import static domain.board.Direction.RIGHT;
import static domain.board.Direction.UP;

import domain.Movement;
import domain.Path;
import java.util.List;

public enum JumpingMovement {

    MA_MOVEMENTS(
            List.of(
                    new Movement(List.of(new Path(List.of(UP))), new Path(List.of(UP, UP, LEFT))),
                    new Movement(List.of(new Path(List.of(UP))), new Path(List.of(UP, UP, RIGHT))),
                    new Movement(List.of(new Path(List.of(RIGHT))), new Path(List.of(RIGHT, RIGHT, UP))),
                    new Movement(List.of(new Path(List.of(RIGHT))), new Path(List.of(RIGHT, RIGHT, DOWN))),
                    new Movement(List.of(new Path(List.of(DOWN))), new Path(List.of(DOWN, DOWN, RIGHT))),
                    new Movement(List.of(new Path(List.of(DOWN))), new Path(List.of(DOWN, DOWN, LEFT))),
                    new Movement(List.of(new Path(List.of(LEFT))), new Path(List.of(LEFT, LEFT, DOWN))),
                    new Movement(List.of(new Path(List.of(LEFT))), new Path(List.of(LEFT, LEFT, UP)))
            )
    ),
    SANG_MOVEMENTS(
            List.of(
                    new Movement(List.of(new Path(List.of(UP)), new Path(List.of(UP, UP, LEFT))),
                            new Path(List.of(UP, UP, UP, LEFT, LEFT))),
                    new Movement(List.of(new Path(List.of(UP)), new Path(List.of(UP, UP, RIGHT))),
                            new Path(List.of(UP, UP, UP, RIGHT, RIGHT))),
                    new Movement(List.of(new Path(List.of(RIGHT)), new Path(List.of(RIGHT, RIGHT, UP))),
                            new Path(List.of(RIGHT, RIGHT, RIGHT, UP, UP))),
                    new Movement(List.of(new Path(List.of(RIGHT)), new Path(List.of(RIGHT, RIGHT, DOWN))),
                            new Path(List.of(RIGHT, RIGHT, RIGHT, DOWN, DOWN))),
                    new Movement(List.of(new Path(List.of(DOWN)), new Path(List.of(DOWN, DOWN, RIGHT))),
                            new Path(List.of(DOWN, DOWN, DOWN, RIGHT, RIGHT))),
                    new Movement(List.of(new Path(List.of(DOWN)), new Path(List.of(DOWN, DOWN, LEFT))),
                            new Path(List.of(DOWN, DOWN, DOWN, LEFT, LEFT))),
                    new Movement(List.of(new Path(List.of(LEFT)), new Path(List.of(LEFT, LEFT, DOWN))),
                            new Path(List.of(LEFT, LEFT, LEFT, DOWN, DOWN))),
                    new Movement(List.of(new Path(List.of(LEFT)), new Path(List.of(LEFT, LEFT, UP))),
                            new Path(List.of(LEFT, LEFT, LEFT, UP, UP)))
            )
    ),
    ;

    private final List<Movement> movements;

    JumpingMovement(List<Movement> movements) {
        this.movements = movements;
    }

    public List<Movement> movements() {
        return movements;
    }
}
