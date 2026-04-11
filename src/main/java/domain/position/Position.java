package domain.position;

import common.exception.JanggiException;

import static common.Constants.*;

public record Position(int row, int column) {
    public Position {
        validate(row, column);
    }

    private void validate(int row, int column) {
        if (row < MIN_ROW || row > MAX_ROW) {
            throw new JanggiException("행값은 %s이상 %s이하여야 합니다. 입력값: %s".formatted(MIN_ROW, MAX_ROW, row));
        }
        if (column < MIN_COLUMN || column > MAX_COLUMN) {
            throw new JanggiException(
                    "열값은 %s이상 %s이하여야 합니다. 입력값: %s".formatted(MIN_COLUMN, MAX_COLUMN, column)
            );
        }
    }

    public boolean isDiagonalWith(Position other) {
        return Math.abs(this.row - other.row) == Math.abs(this.column - other.column);
    }

    public int rowDiff(Position other) {
        return Math.abs(this.row - other.row);
    }

    public int colDiff(Position other) {
        return Math.abs(this.column - other.column);
    }
}
