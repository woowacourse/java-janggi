package model.move;

import model.position.Position;

public record Move(Position from, Position to) {
    public Move {
        validate(from, to);
    }

    public static Move of(Position from, Position to) {
        return new Move(from, to);
    }

    public int diff() {
        if (from.isSameRow(to)) {
            return colDiff();
        }
        if (from.isSameColumn(to)) {
            return rowDiff();
        }

        return 0;
    }

    private int rowDiff() {
        return from.row().diff(to.row());
    }

    private int colDiff() {
        return from.column().diff(to.column());
    }

    private Direction pickStraightDirection() {
        if (rowDiff() < 0) {
            return Direction.UP;
        }
        if (rowDiff() > 0) {
            return Direction.DOWN;
        }
        if (colDiff() < 0) {
            return Direction.LEFT;
        }
        if (colDiff() > 0) {
            return Direction.RIGHT;
        }
        return Direction.NONE;
    }

    private void validate(Position from, Position to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("같은 위치로는 이동할 수 없습니다.");
        }
    }
}
