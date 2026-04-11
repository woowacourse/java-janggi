package model.move;

import model.board.Palace;
import model.position.Position;

public record Move(Position from, Position to) {
    public Move {
        validate(from, to);
    }

    public static Move of(Position from, Position to) {
        return new Move(from, to);
    }

    public Direction direction() {
        int rowDiff = from.row().diff(to.row());
        int colDiff = from.column().diff(to.column());

        return Direction.from(rowDiff, colDiff);
    }

    public int distance() {
        int rowDiff = Math.abs(from.row().diff(to.row()));
        int colDiff = Math.abs(from.column().diff(to.column()));

        return Math.max(rowDiff, colDiff);
    }

    public boolean isStraight() {
        return from.isSameRow(to) || from.isSameColumn(to) || isDiagonal();
    }

    private boolean isDiagonal() {
        if (!Palace.isPalaceBound(from) || !Palace.isPalaceBound(to)) {
            return false;
        }
        return Palace.canMove(from, to);
    }

    private void validate(Position from, Position to) {
        if (from.equals(to)) {
            throw new IllegalArgumentException("[ERROR] 같은 위치로는 이동할 수 없습니다.");
        }
    }
}
