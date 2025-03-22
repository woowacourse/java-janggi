package domain.board;

import static domain.board.Direction.DOWN;
import static domain.board.Direction.LEFT;
import static domain.board.Direction.RIGHT;
import static domain.board.Direction.UP;

import domain.Directions;
import domain.Movement;
import java.util.List;

public enum FixedMovePattern {
    
    MA_MOVEMENTS(
            List.of(
                    new Movement(List.of(new Directions(List.of(UP))), new Directions(List.of(UP, UP, LEFT))),
                    new Movement(List.of(new Directions(List.of(UP))), new Directions(List.of(UP, UP, RIGHT))),
                    new Movement(List.of(new Directions(List.of(RIGHT))), new Directions(List.of(RIGHT, RIGHT, UP))),
                    new Movement(List.of(new Directions(List.of(RIGHT))), new Directions(List.of(RIGHT, RIGHT, DOWN))),
                    new Movement(List.of(new Directions(List.of(DOWN))), new Directions(List.of(DOWN, DOWN, RIGHT))),
                    new Movement(List.of(new Directions(List.of(DOWN))), new Directions(List.of(DOWN, DOWN, LEFT))),
                    new Movement(List.of(new Directions(List.of(LEFT))), new Directions(List.of(LEFT, LEFT, DOWN))),
                    new Movement(List.of(new Directions(List.of(LEFT))), new Directions(List.of(LEFT, LEFT, UP)))
            )
    ),
    SANG_MOVEMENTS(
            List.of(
                    new Movement(List.of(new Directions(List.of(UP)), new Directions(List.of(UP, UP, LEFT))),
                            new Directions(List.of(UP, UP, UP, LEFT, LEFT))),
                    new Movement(List.of(new Directions(List.of(UP)), new Directions(List.of(UP, UP, RIGHT))),
                            new Directions(List.of(UP, UP, UP, RIGHT, RIGHT))),
                    new Movement(List.of(new Directions(List.of(RIGHT)), new Directions(List.of(RIGHT, RIGHT, UP))),
                            new Directions(List.of(RIGHT, RIGHT, RIGHT, UP, UP))),
                    new Movement(List.of(new Directions(List.of(RIGHT)), new Directions(List.of(RIGHT, RIGHT, DOWN))),
                            new Directions(List.of(RIGHT, RIGHT, RIGHT, DOWN, DOWN))),
                    new Movement(List.of(new Directions(List.of(DOWN)), new Directions(List.of(DOWN, DOWN, RIGHT))),
                            new Directions(List.of(DOWN, DOWN, DOWN, RIGHT, RIGHT))),
                    new Movement(List.of(new Directions(List.of(DOWN)), new Directions(List.of(DOWN, DOWN, LEFT))),
                            new Directions(List.of(DOWN, DOWN, DOWN, LEFT, LEFT))),
                    new Movement(List.of(new Directions(List.of(LEFT)), new Directions(List.of(LEFT, LEFT, DOWN))),
                            new Directions(List.of(LEFT, LEFT, LEFT, DOWN, DOWN))),
                    new Movement(List.of(new Directions(List.of(LEFT)), new Directions(List.of(LEFT, LEFT, UP))),
                            new Directions(List.of(LEFT, LEFT, LEFT, UP, UP)))
            )
    ),
    ;

    private final List<Movement> movements;

    FixedMovePattern(List<Movement> movements) {
        this.movements = movements;
    }

    public List<Movement> movements() {
        return movements;
    }
}
