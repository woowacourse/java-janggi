package janggi.domain.move;

import janggi.common.ErrorMessage;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Position {

    private static final int START_COLUMN = 1;
    private static final int LAST_COLUMN = 9;
    private static final int START_ROW = 1;
    private static final int LAST_ROW = 10;
    private static final int PALACE_ROW_MAX_HAN = 3;
    private static final int PALACE_ROW_MIN_CHO = 8;
    private static final int PALACE_COLUMN_START = 4;
    private static final int PALACE_COLUMN_END = 6;

    private static final Position[][] CACHED = new Position[LAST_ROW + 1][LAST_COLUMN + 1];
    private static final Set<Position> AVAILABLE_CROSS_MOVE_POSITION = initializeCrossMovePosition();

    static {
        for (int row = START_ROW; row <= LAST_ROW; row++) {
            for (int column = START_COLUMN; column <= LAST_COLUMN; column++) {
                CACHED[row][column] = new Position(row, column);
            }
        }
    }

    private final int row;
    private final int column;

    private Position(int row, int column) {
        validate(row, column);
        this.row = row;
        this.column = column;
    }

    public static Position of(int row, int column) {
        validate(row, column);
        return CACHED[row][column];
    }

    private static Set<Position> initializeCrossMovePosition() {
        return Set.of(
                new Position(1, 4), new Position(1, 6),
                new Position(2, 5), new Position(3, 4),
                new Position(3, 6), new Position(8, 4),
                new Position(8, 6), new Position(9, 5),
                new Position(10, 4), new Position(10, 6)
        );
    }

    private static void validate(int row, int column) {
        if (!isValid(row, column)) {
            throw new IllegalArgumentException(ErrorMessage.INVALID_BOARD_POSITION.getMessage());
        }
    }

    private static boolean isValid(int row, int column) {
        return row >= START_ROW && row <= LAST_ROW && column >= START_COLUMN && column <= LAST_COLUMN;
    }

    public static int getStartRow() {
        return START_ROW;
    }

    public static int getLastRow() {
        return LAST_ROW;
    }

    public static int getStartColumn() {
        return START_COLUMN;
    }

    public static int getLastColumn() {
        return LAST_COLUMN;
    }

    public Optional<Position> getValidNextPosition(Vector vector) {
        int newRow = this.row + vector.y();
        int newColumn = this.column + vector.x();

        if (isValid(newRow, newColumn)) {
            return Optional.of(Position.of(newRow, newColumn));
        }
        return Optional.empty();
    }

    public Position changeToReverseSide() {
        return Position.of(LAST_ROW - this.row + 1, column);
    }

    public Position moveToNextPosition(Vector vector) {
        int newRow = this.row + vector.y();
        int newColumn = this.column + vector.x();

        return Position.of(newRow, newColumn);
    }

    public boolean canNotMove(Vector vector) {
        int newRow = this.row + vector.y();
        int newColumn = this.column + vector.x();

        return !isValid(newRow, newColumn);
    }

    public boolean canCrossMove() {
        return AVAILABLE_CROSS_MOVE_POSITION.contains(this);
    }

    public boolean isPalace() {
        return isPalaceColumn(column) && isPalaceRow(row);
    }

    private boolean isPalaceRow(int row) {
        return (START_ROW <= row && row <= PALACE_ROW_MAX_HAN) || (PALACE_ROW_MIN_CHO <= row && row <= LAST_ROW);
    }

    private boolean isPalaceColumn(int column) {
        return PALACE_COLUMN_START <= column && column <= PALACE_COLUMN_END;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public String toString() {
        return "Position{" + "row=" + row + ", column=" + column + '}';
    }
}
