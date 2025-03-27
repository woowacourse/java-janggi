package domain.position;

import domain.direction.Direction;

import java.util.List;
import java.util.Objects;

public abstract class JanggiPosition {

    public static final int MIN_ROW = 0;
    public static final int MAX_ROW = 9;
    public static final int MIN_COL = 0;
    public static final int MAX_COL = 8;

    private final int row;
    private final int col;
    private final List<Direction> linked;

    protected JanggiPosition(final int row, final int col, List<Direction> linked) {
        validatePosition(row, col);
        this.row = row;
        this.col = col;
        this.linked = linked;
    }

    public static JanggiPosition of(final int row, final int col) {
        return JanggiPositionFactory.of(row, col);
    }

    private void validatePosition(final int row, final int col) {
        if (!isValid(row, col)) {
            throw new IllegalArgumentException(String.format("위치는 (%d, %d) ~ (%d, %d) 값만 가능합니다.", MIN_ROW, MIN_COL, MAX_ROW, MAX_COL));
        }
    }

    private boolean isValid(final int row, final int col) {
        return row >= MIN_ROW && row <= MAX_ROW && col >= MIN_COL && col <= MAX_COL;
    }

    abstract public boolean isCastle();

    public final boolean canMove(Direction direction) {
        return isValid(row + direction.dr, col + direction.dc);
    }

    public final List<Direction> getLinkedRoadDirections() {
        return linked.stream().filter(this::canMove).toList();
    }

    public final JanggiPosition move(Direction direction) {
        final int nextRow = getRow() + direction.dr;
        final int nextCol = getCol() + direction.dc;
        return JanggiPositionFactory.of(nextRow, nextCol);
    }

    public final int getRow() {
        return row;
    }

    public final int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof JanggiPosition that)) return false;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
