package janggi.moving;

import static janggi.moving.Movement.DOWN;
import static janggi.moving.Movement.DOWN_STRAIGHT;
import static janggi.moving.Movement.LEFT;
import static janggi.moving.Movement.LEFT_DOWN;
import static janggi.moving.Movement.LEFT_STRAIGHT;
import static janggi.moving.Movement.LEFT_UP;
import static janggi.moving.Movement.RIGHT;
import static janggi.moving.Movement.RIGHT_DOWN;
import static janggi.moving.Movement.RIGHT_STRAIGHT;
import static janggi.moving.Movement.RIGHT_UP;
import static janggi.moving.Movement.UP;
import static janggi.moving.Movement.UP_STRAIGHT;

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
            boolean canNotMove = movement.cannotMove(position);
            if (canNotMove) {
                return null;
            }
            position = movement.movePosition(position);
            path.add(position);
        }
        return new Path(path);
    }

    public Path makeStraightPath(Position start, Position goal) {
        List<Position> path = new ArrayList<>();
        path.add(start);
        Position position = start;
        Movement movement = movements.getFirst();
        while (!position.equals(goal)) {
            boolean canNotMove = movement.cannotMove(position);
            if (canNotMove) {
                return null;
            }
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

    public boolean isRightStraight() {
        return movements.contains(RIGHT_STRAIGHT);
    }

    public boolean isLeftStraight() {
        return movements.contains(LEFT_STRAIGHT);
    }

    public boolean isUpStraight() {
        return movements.contains(UP_STRAIGHT);
    }

    public boolean isDownStraight() {
        return movements.contains(DOWN_STRAIGHT);
    }

    public boolean isStraight() {
        return isUpStraight() || isDownStraight() || isLeftStraight() || isRightStraight();
    }
}
