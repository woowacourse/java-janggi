package domain.position;

import static common.exception.ErrorMessage.INVALID_COLUMN_RANGE;
import static common.exception.ErrorMessage.INVALID_ROW_RANGE;
import static domain.board.Board.MAX_COLUMN;
import static domain.board.Board.MAX_ROW;
import static domain.board.Board.MIN_COLUMN;
import static domain.board.Board.MIN_ROW;

import common.exception.JanggiException;

public record Position(int row, int column) {
    public Position {
        validate(row, column);
    }

    public static boolean isValid(int row, int column) {
        return row >= MIN_ROW && row <= MAX_ROW && column >= MIN_COLUMN && column <= MAX_COLUMN;
    }

    private void validate(int row, int column) {
        if (row < MIN_ROW || row > MAX_ROW) {
            throw new JanggiException(INVALID_ROW_RANGE.formatted(MIN_ROW, MAX_ROW, row));
        }
        if (column < MIN_COLUMN || column > MAX_COLUMN) {
            throw new JanggiException(INVALID_COLUMN_RANGE.formatted(MIN_COLUMN, MAX_COLUMN, column));
        }
    }
}
