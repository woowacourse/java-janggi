package domain.game;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.Down;
import domain.direction.MoveAmount;
import domain.direction.Up;
import java.util.List;

public enum Side {
    HAN(Intersection.LOWER_BOUND_ROW, Intersection.UPPER_BOUND_FILE, new Down()) {
        @Override
        public Side nextTurn() {
            return CHO;
        }
    },
    CHO(Intersection.UPPER_BOUND_ROW, Intersection.LOWER_BOUND_FILE, new Up()) {
        @Override
        public Side nextTurn() {
            return HAN;
        }
    },
    NONE(Intersection.IGNORED, Intersection.IGNORED, new NoDirection()) {
        @Override
        public Side nextTurn() {
            return NONE;
        }
    },
    ;

    private final int baseRow;
    private final int baseFile;
    private final Direction forwardDirection;

    Side(
            int baseRow,
            int baseFile,
            Direction forwardDirection
    ) {
        this.baseRow = baseRow;
        this.baseFile = baseFile;
        this.forwardDirection = forwardDirection;
    }

    public int getRowAt(MoveAmount distanceFromBaseRow) {
        final int defaultFile = Intersection.IGNORED;
        Intersection targetIntersection =
                forwardDirection.moveForward(new Intersection(baseRow, defaultFile), distanceFromBaseRow);

        return targetIntersection.row();
    }

    public int getFileAt(MoveAmount distanceFromBaseFile) {
        final int defaultRow = Intersection.IGNORED;
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
                forwardDirection.left(), forwardDirection.right()
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
        public Direction left() {
            return this;
        }

        @Override
        public Direction right() {
            return this;
        }

        @Override
        public Direction reverse() {
            return this;
        }
    }
}
