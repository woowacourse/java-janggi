package domain.game;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.Down;
import domain.direction.Left;
import domain.direction.MoveAmount;
import domain.direction.Right;
import domain.direction.Up;
import java.util.List;

public enum Side {
    HAN(1, 9, new Down(), new Right()) {
        @Override
        public Side nextTurn() {
            return CHO;
        }
    },
    CHO(10, 1, new Up(), new Left()) {
        @Override
        public Side nextTurn() {
            return HAN;
        }
    },
    NONE(0, 0, new NoDirection(), new NoDirection()) {
        @Override
        public Side nextTurn() {
            return NONE;
        }
    },
    ;

    private final int baseRow;
    private final int baseFile;
    private final Direction forwardDirection;
    private final Direction leftDirection;

    Side(
            int baseRow,
            int baseFile,
            Direction forwardDirection,
            Direction leftDirection
    ) {
        this.baseRow = baseRow;
        this.baseFile = baseFile;
        this.forwardDirection = forwardDirection;
        this.leftDirection = leftDirection;
    }

    public int getRowAt(MoveAmount distanceFromBaseRow) {
        // TODO 이 부분도 명령하는 방식으로 바꿀 수 있을 듯
        final int defaultFile = 5;
        Intersection targetIntersection =
                forwardDirection.moveForward(new Intersection(baseRow, defaultFile), distanceFromBaseRow);

        return targetIntersection.row();
    }

    public int getFileAt(MoveAmount distanceFromBaseFile) {
        final int defaultRow = 5;
        Intersection targetIntersection =
                forwardDirection.moveRight(new Intersection(defaultRow, baseFile), distanceFromBaseFile);

        return targetIntersection.file();
    }

    public Intersection moveForward(Intersection from, MoveAmount moveAmount) {
        return forwardDirection.moveForward(from, moveAmount);
    }

    public Intersection moveLeft(Intersection from, MoveAmount moveAmount) {
        return forwardDirection.moveLeft(from, moveAmount);
    }

    public Intersection moveRight(Intersection from, MoveAmount moveAmount) {
        return forwardDirection.moveRight(from, moveAmount);
    }

    public Intersection moveForwardLeft(Intersection from, MoveAmount moveAmount) {
        return forwardDirection.moveForwardLeft(from, moveAmount);
    }

    public Intersection moveForwardRight(Intersection from, MoveAmount moveAmount) {
        return forwardDirection.moveForwardRight(from, moveAmount);
    }

    public Intersection moveBackward(Intersection from, MoveAmount moveAmount) {
        return forwardDirection.reverse().moveForward(from, moveAmount);
    }

    public Intersection moveBackwardLeft(Intersection from, MoveAmount moveAmount) {
        return forwardDirection.reverse().moveForwardRight(from, moveAmount);
    }

    public Intersection moveBackwardRight(Intersection from, MoveAmount moveAmount) {
        return forwardDirection.reverse().moveForwardLeft(from, moveAmount);
    }

    public List<Direction> getAllDirections() {
        return List.of(
                forwardDirection, forwardDirection.reverse(),
                leftDirection, leftDirection.reverse()
        );
    }

    public abstract Side nextTurn();

    private static class NoDirection implements Direction {
        @Override
        public Intersection moveForward(Intersection current, MoveAmount amount) {
            return current;
        }

        @Override
        public Intersection moveLeft(Intersection current, MoveAmount amount) {
            return current;
        }

        @Override
        public Intersection moveRight(Intersection current, MoveAmount amount) {
            return current;
        }

        @Override
        public Intersection moveForwardLeft(Intersection current, MoveAmount amount) {
            return current;
        }

        @Override
        public Intersection moveForwardRight(Intersection current, MoveAmount amount) {
            return current;
        }

        @Override
        public Direction reverse() {
            return this;
        }
    }
}
