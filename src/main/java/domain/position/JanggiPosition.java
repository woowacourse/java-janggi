package domain.position;

import domain.direction.Direction;
import domain.position.castle.*;
import domain.position.normal.NormalPosition;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class JanggiPosition {
    private enum JanggiPositionFactory {
        RED_LEFT_TOP_CASTLE(new LeftTopCastlePosition(0, 3)),
        RED_LEFT_MIDDLE_CASTLE(new LeftMiddleCastlePosition(1, 3)),
        RED_LEFT_BOTTOM_CASTLE(new LeftBottomCastlePosition(2, 3)),
        RED_MIDDLE_TOP_CASTLE(new MiddleTopCastlePosition(0, 4)),
        RED_CENTER_CASTLE(new CenterCastlePosition(1, 4)),
        RED_MIDDLE_BOTTOM_CASTLE(new MiddleBottomCastlePosition(2, 4)),
        RED_RIGHT_TOP_CASTLE(new RightTopCastlePosition(0, 5)),
        RED_RIGHT_MIDDLE_CASTLE(new RightMiddleCastlePosition(1, 5)),
        RED_RIGHT_BOTTOM_CASTLE(new RightBottomCastlePosition(2, 5)),

        BLUE_LEFT_TOP_CASTLE(new LeftTopCastlePosition(7, 3)),
        BLUE_LEFT_MIDDLE_CASTLE(new LeftMiddleCastlePosition(8, 3)),
        BLUE_LEFT_BOTTOM_CASTLE(new LeftBottomCastlePosition(9, 3)),
        BLUE_MIDDLE_TOP_CASTLE(new MiddleTopCastlePosition(7, 4)),
        BLUE_CENTER_CASTLE(new CenterCastlePosition(8, 4)),
        BLUE_MIDDLE_BOTTOM_CASTLE(new MiddleBottomCastlePosition(9, 4)),
        BLUE_RIGHT_TOP_CASTLE(new RightTopCastlePosition(7, 5)),
        BLUE_RIGHT_MIDDLE_CASTLE(new RightMiddleCastlePosition(8, 5)),
        BLUE_RIGHT_BOTTOM_CASTLE(new RightBottomCastlePosition(9, 5)),
        ;

        private final JanggiPosition position;

        JanggiPositionFactory(JanggiPosition position) {
            this.position = position;
        }

        public static JanggiPosition of(final int row, final int col) {
            return Arrays.stream(values())
                    .map(JanggiPositionFactory::getPosition)
                    .filter(p -> p.getRow() == row && p.getCol() == col)
                    .findFirst()
                    .orElse(new NormalPosition(row, col));
        }

        public JanggiPosition getPosition() {
            return position;
        }
    }

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
