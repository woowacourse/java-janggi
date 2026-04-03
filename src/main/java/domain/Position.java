package domain;

import java.util.Arrays;
import java.util.function.Predicate;

public record Position(int row, int col) {

    public static Position of(int row, int col) {
        return new Position(row, col);
    }

    public Position up() {
        return Position.of(this.row - 1, this.col);
    }

    public Position down() {
        return Position.of(this.row + 1, this.col);
    }

    public Position left() {
        return Position.of(this.row, this.col - 1);
    }

    public Position right() {
        return Position.of(this.row, this.col + 1);
    }

    public Position upCrossRight() {
        return Position.of(this.row - 1, this.col + 1);
    }

    public Position upCrossLeft() {
        return Position.of(this.row - 1, this.col - 1);
    }

    public Position downCrossRight() {
        return Position.of(this.row + 1, this.col + 1);
    }

    public Position downCrossLeft() {
        return Position.of(this.row + 1, this.col - 1);
    }

    public boolean isOutOfBoard() {
        return this.row < BoardRange.ROW.min
                || this.row > BoardRange.ROW.max
                || this.col < BoardRange.COL.min
                || this.col > BoardRange.COL.max;
    }

    public boolean isInPalace() {
        return PalaceRange.isInPalace(this.row, this.col);
    }

    public boolean isPalaceRedEastNorth() {
        return Palace.isRedEastNorth(this.row, this.col);
    }

    public boolean isPalaceRedEastSouth() {
        return Palace.isRedEastSouth(this.row, this.col);
    }

    public boolean isPalaceRedWestNorth() {
        return Palace.isRedWestNorth(this.row, this.col);
    }

    public boolean isPalaceRedWestSouth() {
        return Palace.isRedWestSouth(this.row, this.col);
    }

    public boolean isPalaceRedCenter() {
        return Palace.isRedCenter(this.row, this.col);
    }

    public boolean isPalaceGreenEastNorth() {
        return Palace.isIsGreenEastNorth(this.row, this.col);
    }

    public boolean isPalaceGreenEastSouth() {
        return Palace.isGreenEastSouth(this.row, this.col);
    }

    public boolean isPalaceGreenWestNorth() {
        return Palace.isGreenWestNorth(this.row, this.col);
    }

    public boolean isPalaceGreenWestSouth() {
        return Palace.isGreenWestSouth(this.row, this.col);
    }

    public boolean isPalaceGreenCenter() {
        return Palace.isGreenCenter(this.row, this.col);
    }

    public boolean isPalaceSide() {
        return PalaceRange.isInPalace(this.row, this.col) && Palace.isNotConnerOrCenter(this.row, this.col);
    }

    private enum BoardRange {
        ROW(0, 9),
        COL(0, 8),
        ;

        final int min;
        final int max;

        BoardRange(int min, int max) {
            this.min = min;
            this.max = max;
        }
    }

    private enum PalaceRange {
        RED(0, 2, 3, 5),
        GREEN(7, 9, 3, 5),
        ;

        final int minRow, maxRow, minCol, maxCol;

        PalaceRange(int minRow, int maxRow, int minCol, int maxCol) {
            this.minRow = minRow;
            this.maxRow = maxRow;
            this.minCol = minCol;
            this.maxCol = maxCol;
        }

         static boolean isInPalace(int row, int col) {
            return Arrays.stream(PalaceRange.values())
                    .anyMatch(range -> range.contains(row, col));
        }

        private boolean contains(int row, int col) {
            return row >= minRow && row <= maxRow && col >= minCol && col <= maxCol;
        }
    }


    private enum Palace {
        RED_EAST_NORTH(current -> current.equals(Position.of(0, 5))),
        RED_EAST_SOUTH(current -> current.equals(Position.of(2,5))),

        RED_WEST_NORTH(current -> current.equals(Position.of(0,3))),
        RED_WEST_SOUTH(current -> current.equals(Position.of(2,3))),

        RED_CENTER(current -> current.equals(Position.of(1,4))),

        GREEN_EAST_NORTH(current -> current.equals(Position.of(7,5))),
        GREEN_EAST_SOUTH(current -> current.equals(Position.of(9,5))),

        GREEN_WEST_NORTH(current -> current.equals(Position.of(7,3))),
        GREEN_WEST_SOUTH(current -> current.equals(Position.of(9,3))),

        GREEN_CENTER(current -> current.equals(Position.of(8,4))),
        ;

        private final Predicate<Position> predicate;

        Palace(Predicate<Position> predicate) {
            this.predicate = predicate;
        }

        private static boolean isNotConnerOrCenter (int row, int col) {
            return Arrays.stream(Palace.values()).map(palace -> palace.predicate)
                    .anyMatch(positionPredicate -> positionPredicate.test(Position.of(row, col)));
        }

        private static boolean isRedEastNorth(int row, int col) {
            return RED_EAST_NORTH.predicate.test(Position.of(row, col));
        }

        private static boolean isRedEastSouth(int row, int col) {
            return RED_EAST_SOUTH.predicate.test(Position.of(row, col));
        }

        private static boolean isRedWestNorth(int row, int col) {
            return RED_WEST_NORTH.predicate.test(Position.of(row, col));
        }

        private static boolean isRedWestSouth(int row, int col) {
            return RED_WEST_SOUTH.predicate.test(Position.of(row, col));
        }

        private static boolean isRedCenter(int row, int col) {
            return RED_CENTER.predicate.test(Position.of(row, col));
        }

        private static boolean isIsGreenEastNorth(int row, int col) {
            return GREEN_EAST_NORTH.predicate.test(Position.of(row, col));
        }

        private static boolean isGreenEastSouth(int row, int col) {
            return GREEN_EAST_SOUTH.predicate.test(Position.of(row, col));
        }

        private static boolean isGreenWestNorth(int row, int col) {
            return GREEN_WEST_NORTH.predicate.test(Position.of(row, col));
        }

        private static boolean isGreenWestSouth(int row, int col) {
            return GREEN_WEST_SOUTH.predicate.test(Position.of(row, col));
        }

        private static boolean isGreenCenter(int row, int col) {
            return GREEN_CENTER.predicate.test(Position.of(row, col));
        }
    }
}
