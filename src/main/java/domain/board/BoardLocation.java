package domain.board;

import java.util.Objects;

public record BoardLocation(
        int column,
        int row
) {

    private static final int START_COLUMN = 1;
    private static final int START_ROW = 1;
    private static final int END_COLUMN = 9;
    private static final int END_ROW = 10;

    public BoardLocation {
        validateXRange(column);
        validateYRange(row);
    }

    public boolean isDown(BoardLocation current) {
        return current.row - row < 0;
    }

    public boolean isUp(BoardLocation current) {
        return current.row - row > 0;
    }

    public BoardLocation move(int dx, int dy) {
        return new BoardLocation(this.column + dx, this.row + dy);
    }

    public BoardLocation moveVertical(int dy) {
        return new BoardLocation(column, row + dy);
    }

    public BoardLocation moveHorizon(int dx) {
        return new BoardLocation(column + dx, row);
    }

    public BoardLocation moveDirection(MoveDirection moveDirection) {
        return this.move(moveDirection.column(), moveDirection.row());
    }

    private void validateYRange(int row) {
        if (row < START_ROW || row > END_ROW) {
            throw new IllegalArgumentException("[ERROR] 보드판 y값이 범위 밖에 위치합니다. row :" + row);
        }
    }

    private void validateXRange(int column) {
        if (column < START_COLUMN || column > END_COLUMN) {
            throw new IllegalArgumentException("[ERROR] 보드판 x값이 범위 밖에 위치합니다. column :" + column);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoardLocation boardLocation)) {
            return false;
        }
        return column == boardLocation.column && row == boardLocation.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }
}
