package model.coordinate;

import static model.board.Board.BOARD_COL;
import static model.board.Board.BOARD_ROW;

public record Position(int row, int col) {

    public static final Position HAN_LEFT_OUTER = new Position(0, 1);
    public static final Position HAN_LEFT_INNER = new Position(0, 2);
    public static final Position HAN_RIGHT_INNER = new Position(0, 6);
    public static final Position HAN_RIGHT_OUTER = new Position(0, 7);

    public static final Position CHO_LEFT_OUTER = new Position(9, 1);
    public static final Position CHO_LEFT_INNER = new Position(9, 2);
    public static final Position CHO_RIGHT_INNER = new Position(9, 6);
    public static final Position CHO_RIGHT_OUTER = new Position(9, 7);

    public Position {
        if (row < 0 || row >= BOARD_ROW) {
            throw new IllegalArgumentException("장기판의 행 범위를 벗어났습니다. 최대 범위: " + BOARD_ROW);
        }

        if (col < 0 || col >= BOARD_COL) {
            throw new IllegalArgumentException("장기판의 열 범위를 벗어났습니다. 최대 범위: " + BOARD_COL);
        }
    }

    public Displacement minus(Position other) {
        return new Displacement(calculateRowDiff(other), calculateColDiff(other));
    }

    public int calculateRowDiff(Position other) {
        return this.row() - other.row();
    }

    public int calculateColDiff(Position other) {
        return this.col - other.col();
    }

    public Position move(Direction direction) {
        return new Position(row + direction.rowDelta(), col + direction.colDelta());
    }
}
