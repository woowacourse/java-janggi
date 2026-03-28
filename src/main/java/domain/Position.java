package domain;

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
}
