package janggi;

import static janggi.Movement.DOWN;
import static janggi.Movement.LEFT;
import static janggi.Movement.LEFT_DOWN;
import static janggi.Movement.LEFT_UP;
import static janggi.Movement.RIGHT;
import static janggi.Movement.RIGHT_DOWN;
import static janggi.Movement.RIGHT_UP;
import static janggi.Movement.UP;

import janggi.board.position.Position;
import java.util.ArrayList;
import java.util.List;

public class Movements {
    private final List<Movement> movements;

    public Movements(List<Movement> movements) {
        this.movements = movements;
    }

    public Movements(Movement... movements) {
        this(List.of(movements));
    }

    public Path makePath(Position start) {
        List<Position> path = new ArrayList<>();
        path.add(start);
        Position position = start;
        for (Movement movement : movements) {
            position = movement.movePosition(position);
            path.add(position);
        }
        return new Path(path);
    }

    public boolean isUpward() {
        return movements.contains(UP) ||
                movements.contains(LEFT_UP) ||
                movements.contains(RIGHT_UP);
    }

    public boolean isDownward() {
        return movements.contains(DOWN) ||
                movements.contains(LEFT_DOWN) ||
                movements.contains(RIGHT_DOWN);
    }

    public boolean isLeftward() {
        return movements.contains(LEFT) ||
                movements.contains(LEFT_UP) ||
                movements.contains(LEFT_DOWN);
    }

    public boolean isRightward() {
        return movements.contains(RIGHT) ||
                movements.contains(RIGHT_UP) ||
                movements.contains(RIGHT_DOWN);
    }
}
