package domain.board;

import static domain.board.Path.DOWN_DOWN_DOWN_LEFT_LEFT_PATH;
import static domain.board.Path.DOWN_DOWN_DOWN_RIGHT_RIGHT_PATH;
import static domain.board.Path.DOWN_DOWN_LEFT_PATH;
import static domain.board.Path.DOWN_DOWN_RIGHT_PATH;
import static domain.board.Path.DOWN_PATH;
import static domain.board.Path.LEFT_LEFT_DOWN_PATH;
import static domain.board.Path.LEFT_LEFT_LEFT_DOWN_DOWN_PATH;
import static domain.board.Path.LEFT_LEFT_LEFT_UP_UP_PATH;
import static domain.board.Path.LEFT_LEFT_UP_PATH;
import static domain.board.Path.LEFT_PATH;
import static domain.board.Path.RIGHT_PATH;
import static domain.board.Path.RIGHT_RIGHT_DOWN_PATH;
import static domain.board.Path.RIGHT_RIGHT_RIGHT_DOWN_DOWN_PATH;
import static domain.board.Path.RIGHT_RIGHT_RIGHT_UP_UP_PATH;
import static domain.board.Path.RIGHT_RIGHT_UP_PATH;
import static domain.board.Path.UP_PATH;
import static domain.board.Path.UP_UP_LEFT_PATH;
import static domain.board.Path.UP_UP_RIGHT_PATH;
import static domain.board.Path.UP_UP_UP_LEFT_LEFT_PATH;
import static domain.board.Path.UP_UP_UP_RIGHT_RIGHT_PATH;

import java.util.List;

public enum JumpingMovements {

    MA(
            List.of(
                    new Movement(List.of(UP_PATH), UP_UP_LEFT_PATH),
                    new Movement(List.of(UP_PATH), UP_UP_RIGHT_PATH),
                    new Movement(List.of(RIGHT_PATH), RIGHT_RIGHT_UP_PATH),
                    new Movement(List.of(RIGHT_PATH), RIGHT_RIGHT_DOWN_PATH),
                    new Movement(List.of(DOWN_PATH), DOWN_DOWN_RIGHT_PATH),
                    new Movement(List.of(DOWN_PATH), DOWN_DOWN_LEFT_PATH),
                    new Movement(List.of(LEFT_PATH), LEFT_LEFT_DOWN_PATH),
                    new Movement(List.of(LEFT_PATH), LEFT_LEFT_UP_PATH)
            )
    ),
    SANG(
            List.of(
                    new Movement(List.of(UP_PATH, UP_UP_LEFT_PATH), UP_UP_UP_LEFT_LEFT_PATH),
                    new Movement(List.of(UP_PATH, UP_UP_RIGHT_PATH), UP_UP_UP_RIGHT_RIGHT_PATH),
                    new Movement(List.of(RIGHT_PATH, RIGHT_RIGHT_UP_PATH), RIGHT_RIGHT_RIGHT_UP_UP_PATH),
                    new Movement(List.of(RIGHT_PATH, RIGHT_RIGHT_DOWN_PATH), RIGHT_RIGHT_RIGHT_DOWN_DOWN_PATH),
                    new Movement(List.of(DOWN_PATH, DOWN_DOWN_RIGHT_PATH), DOWN_DOWN_DOWN_RIGHT_RIGHT_PATH),
                    new Movement(List.of(DOWN_PATH, DOWN_DOWN_LEFT_PATH), DOWN_DOWN_DOWN_LEFT_LEFT_PATH),
                    new Movement(List.of(LEFT_PATH, LEFT_LEFT_DOWN_PATH), LEFT_LEFT_LEFT_DOWN_DOWN_PATH),
                    new Movement(List.of(LEFT_PATH, LEFT_LEFT_UP_PATH), LEFT_LEFT_LEFT_UP_UP_PATH)
            )
    ),
    ;

    private final List<Movement> movements;

    JumpingMovements(List<Movement> movements) {
        this.movements = movements;
    }

    public List<Movement> movements() {
        return movements;
    }
}
