package position;

import java.util.List;
import pieces.Side;

public record Delta(int rowDelta, int columnDelta) {

    private static final int ONE_STEP = 1;
    private static final int NO_CHANGE = 0;

    public Delta add(Delta delta) {
        return new Delta(rowDelta + delta.rowDelta, columnDelta + delta.columnDelta);
    }

    public static Delta up() {
        return new Delta(ONE_STEP, NO_CHANGE);
    }

    public static Delta down() {
        return new Delta(Math.negateExact(ONE_STEP), NO_CHANGE);
    }

    public static Delta right() {
        return new Delta(NO_CHANGE, ONE_STEP);
    }

    public static Delta left() {
        return new Delta(NO_CHANGE, Math.negateExact(ONE_STEP));
    }

    public static Delta rightUp() {
        return right().add(up());
    }

    public static Delta leftUp() {
        return left().add(up());
    }

    public static Delta rightDown() {
        return right().add(down());
    }

    public static Delta leftDown() {
        return left().add(down());
    }

    public static List<Delta> getDiagonals() {
        return List.of(
            Delta.rightUp(),
            Delta.leftUp(),
            Delta.rightDown(),
            Delta.leftDown()
        );
    }

    public boolean isOneStepDiagonal() {
        return Math.abs(rowDelta) == ONE_STEP && Math.abs(columnDelta) == ONE_STEP;
    }

    public boolean isForwardOneStepDiagonal(Side side) {
        return rowDelta == side.forwardDelta().rowDelta && Math.abs(columnDelta) == ONE_STEP;
    }
}
