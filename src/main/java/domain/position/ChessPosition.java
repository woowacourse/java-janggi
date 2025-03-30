package domain.position;

import domain.direction.Direction;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public record ChessPosition(
        int row,
        int column
) {
    public static final int MIN_ROW = 0;
    public static final int MAX_ROW = 9;
    public static final int MIN_COL = 0;
    public static final int MAX_COL = 8;

    private static final int TOP_PALACE_MIN_ROW = 0;
    private static final int TOP_PALACE_MAX_ROW = 2;
    private static final int BOTTOM_PALACE_MIN_ROW = 7;
    private static final int BOTTOM_PALACE_MAX_ROW = 8;
    private static final int PALACE_MIN_COL = 3;
    private static final int PALACE_MAX_COL = 5;

    private static final List<ChessPosition> CASTLE_PSOTIONS = List.of(
            new ChessPosition(9, 3),
            new ChessPosition(9,5),
            new ChessPosition(7, 3),
            new ChessPosition(7, 5),
            new ChessPosition(8, 4),
            new ChessPosition(0, 3),
            new ChessPosition(0, 5),
            new ChessPosition(2, 3),
            new ChessPosition(2, 5),
            new ChessPosition(1, 4)
    );
    private static final Map<Direction, Predicate<ChessPosition>> DIRECTION_FILTERS = Map.of(
            Direction.LEFT_UP, ChessPosition::isCastleMoveValid,
            Direction.LEFT_DOWN, ChessPosition::isCastleMoveValid,
            Direction.RIGHT_UP, ChessPosition::isCastleMoveValid,
            Direction.RIGHT_DOWN, ChessPosition::isCastleMoveValid,
            Direction.UP, ChessPosition::isValid,
            Direction.DOWN, ChessPosition::isValid,
            Direction.LEFT, ChessPosition::isValid,
            Direction.RIGHT, ChessPosition::isValid
    );

    public ChessPosition {
        if (!isValid(row, column)) {
            throw new IllegalArgumentException(
                    String.format("위치는 (%d, %d) ~ (%d, %d) 값만 가능합니다.", MIN_ROW, MIN_COL, MAX_ROW, MAX_COL)
            );
        }
    }

    public boolean isCastlePosition() {
        return CASTLE_PSOTIONS.contains(this);
    }

    public boolean isInCastle() {
        return ((row >= TOP_PALACE_MIN_ROW && row <= TOP_PALACE_MAX_ROW)
                || (row >= BOTTOM_PALACE_MIN_ROW && row <= BOTTOM_PALACE_MAX_ROW))
                && (column >= PALACE_MIN_COL && column <= PALACE_MAX_COL);
    }


    public boolean canMove(Direction direction) {
        return isValid(row + direction.dr, column + direction.dc);
    }

    public boolean canCastleMove(Direction direction) {
        if (direction.isDiagonal() && !isCastlePosition()) {
            return false;
        }
        return DIRECTION_FILTERS.get(direction).test(this.move(direction));
    }

    public ChessPosition move(Direction direction) {
        return new ChessPosition(row + direction.dr, column + direction.dc);
    }

    private boolean isCastleMoveValid() {
        return isValid(row, column) && isInCastle();
    }

    private boolean isValid() {
        return isValid(row, column);
    }

    private boolean isValid(final int row, final int col) {
        return row >= MIN_ROW && row <= MAX_ROW && col >= MIN_COL && col <= MAX_COL;
    }
}
