package janggi.domain.vo;

import janggi.domain.Direction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Position {
    private static final int MIN_ROW = 0;
    private static final int MAX_ROW = 9;
    private static final int MIN_COL = 0;
    private static final int MAX_COL = 8;

    private final int row;
    private final int col;

    public Position(int row, int col) {
        validateRange(row, col);

        this.row = row;
        this.col = col;
    }

    public List<Position> generatePath(List<Direction> directions) {
        List<Position> path = new ArrayList<>();
        Position current = this;

        for (Direction direction : directions) {
            if (!current.hasNext(direction)) {
                return Collections.emptyList();
            }

            current = current.nextPosition(direction);
            path.add(current);
        }

        return path;
    }

    public boolean isOnSameRow(Position other) {
        return this.getRow() == other.getRow();
    }

    public boolean isOnSameCol(Position other) {
        return this.getCol() == other.getCol();
    }

    public boolean isStraightLine(Position other) {
        return this.isOnSameRow(other) || this.isOnSameCol(other);
    }

    public Position nextPosition(Direction direction) {
        return new Position(this.getRow() + direction.getDx(), this.getCol() + direction.getDy());
    }

    public boolean hasNext(Direction direction) {
        return canMakePositionOnBoard(this.getRow() + direction.getDx(), this.getCol() + direction.getDy());
    }

    private boolean canMakePositionOnBoard(int row, int col) {
        return row >= MIN_ROW && row <= MAX_ROW && col >= MIN_COL && col <= MAX_COL;
    }

    private void validateRange(int row, int col) {
        if (row < MIN_ROW || row > MAX_ROW) {
            throw new IllegalArgumentException("범위 밖의 행입니다.");
        }

        if (col < MIN_COL || col > MAX_COL) {
            throw new IllegalArgumentException("범위 밖의 열입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
