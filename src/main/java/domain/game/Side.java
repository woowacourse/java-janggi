package domain.game;

import domain.movement.Vector;
import domain.movement.direction.Direction;
import domain.movement.direction.Down;
import domain.movement.direction.Up;
import java.util.List;

public enum Side {
    HAN(1, new Down()) {
        @Override
        public Side nextTurn() {
            return CHO;
        }
    },
    CHO(10, new Up()) {
        @Override
        public Side nextTurn() {
            return HAN;
        }
    },
    ;

    private final int baseRow;
    private final Direction direction;

    Side(
            int baseRow,
            Direction forwardDirection
    ) {
        this.baseRow = baseRow;
        this.direction = forwardDirection;
    }

    public int calculateRowFromBase(int farFromBaseRow) {
        int rowDelta = direction.toForward()
                .rowDelta();

        return baseRow + (rowDelta * farFromBaseRow);
    }

    public Vector toForward() {
        return direction.toForward();
    }

    public Vector toBackward() {
        return direction.toBackword();
    }

    public Vector toLeft() {
        return direction.toLeft();
    }

    public Vector toRight() {
        return direction.toRight();
    }

    public List<Vector> getAllDirections() {
        return List.of(
                toForward(),
                toBackward(),
                toLeft(),
                toRight()
        );
    }

    public abstract Side nextTurn();
}
