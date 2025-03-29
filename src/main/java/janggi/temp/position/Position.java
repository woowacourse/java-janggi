package janggi.temp.position;

import janggi.temp.movement.ElephantMovement;
import janggi.temp.movement.HorseMovement;
import janggi.temp.movement.Movement;
import java.util.List;

public record Position(Column column, Row row) {

    /*
     * 한의 궁성 (column, row)
     * (3, 0) (4, 0) (5, 0)
     * (3, 1) (4, 1) (5, 1)
     * (3, 2) (4, 2) (5, 2)
     *
     * 초의 궁성 (column, row)
     * (3, 7) (4, 7) (5, 7)
     * (3, 8) (4, 8) (5, 8)
     * (3, 9) (4, 9) (5, 9)
     */

    private static final List<Position> palace = List.of(
            new Position(Column.THREE, Row.ZERO),
            new Position(Column.FOUR, Row.ZERO),
            new Position(Column.FIVE, Row.ZERO),
            new Position(Column.THREE, Row.ONE),
            new Position(Column.FOUR, Row.ONE),
            new Position(Column.FIVE, Row.ONE),
            new Position(Column.THREE, Row.TWO),
            new Position(Column.FOUR, Row.TWO),
            new Position(Column.FIVE, Row.TWO),
            new Position(Column.THREE, Row.SEVEN),
            new Position(Column.FOUR, Row.SEVEN),
            new Position(Column.FIVE, Row.SEVEN),
            new Position(Column.THREE, Row.EIGHT),
            new Position(Column.FOUR, Row.EIGHT),
            new Position(Column.FIVE, Row.EIGHT),
            new Position(Column.THREE, Row.NINE),
            new Position(Column.FOUR, Row.NINE),
            new Position(Column.FIVE, Row.NINE)
    );

    private static final List<Position> palaceSide = List.of(
            new Position(Column.FOUR, Row.ZERO),
            new Position(Column.THREE, Row.ONE),
            new Position(Column.FIVE, Row.ONE),
            new Position(Column.FOUR, Row.TWO),
            new Position(Column.FOUR, Row.SEVEN),
            new Position(Column.THREE, Row.EIGHT),
            new Position(Column.FOUR, Row.NINE)
    );

    public Position move(final Movement movement) {
        final Column targetColumn = column.move(movement.columnValue());
        final Row targetRow = row.move(movement.rowValue());
        return new Position(targetColumn, targetRow);
    }

    public Position move(final HorseMovement movement) {
        final Column targetColumn = column.move(movement.columnValue());
        final Row targetRow = row.move(movement.rowValue());
        return new Position(targetColumn, targetRow);
    }

    public Position move(final ElephantMovement movement) {
        final Column targetColumn = column.move(movement.columnValue());
        final Row targetRow = row.move(movement.rowValue());
        return new Position(targetColumn, targetRow);
    }

    public boolean canMove(final Movement movement) {
        final boolean canMoveColumn = column.canMove(movement.columnValue());
        final boolean canMoveRow = row.canMove(movement.rowValue());
        return canMoveColumn && canMoveRow;
    }

    public boolean canMove(final HorseMovement movement) {
        final boolean canMoveColumn = column.canMove(movement.columnValue());
        final boolean canMoveRow = row.canMove(movement.rowValue());
        return canMoveColumn && canMoveRow;
    }

    public boolean canMove(final ElephantMovement movement) {
        final boolean canMoveColumn = column.canMove(movement.columnValue());
        final boolean canMoveRow = row.canMove(movement.rowValue());
        return canMoveColumn && canMoveRow;
    }

    public boolean isOrthogonallyAligned(final Position other) {
        return this.row == other.row || this.column == other.column;
    }

    public boolean isDiagonallyAlignedInPalace(final Position other) {
        return isPalace() && other.isPalace() && isDiagonallyAligned(other);
    }

    public Movement getMovement(final Position to) {
        if (this.equals(to)) {
            throw new IllegalStateException("[ERROR] 같은 위치입니다.");
        }

        if (this.column == to.column) { // 같은 열
            if (this.row.difference(to.row) < 0) { // to의 행번호가 더 크다
                return Movement.DOWN;
            }
            if (this.row.difference(to.row) > 0) { // to의 열번호가 더 작다
                return Movement.UP;
            }
        }
        if (this.row == to.row) { // 같은 행
            if (this.column.difference(to.column) < 0) { // to의 행번호가 더 크다
                return Movement.RIGHT;
            }
            if (this.column.difference(to.column) > 0) { // to의 행번호가 더 작다
                return Movement.LEFT;
            }
        }
        if (isDiagonallyAligned(to)) { // 대각선
            if (this.column.difference(to.column) < 0) { // to가 열 번호가 더 크다
                if (this.row.difference(to.row) < 0) { // to가 행 번호가 더 크다
                    return Movement.RIGHT_DOWN;
                }
                if (this.row.difference(to.row) > 0) { // to가 행 번호가 더 작다
                    return Movement.RIGHT_UP;
                }
            }
            if (this.column.difference(to.column) > 0) { // to가 열 번호가 더 작다
                if (this.row.difference(to.row) < 0) { // to가 행 번호가 더 크다
                    return Movement.LEFT_DOWN;
                }
                if (this.row.difference(to.row) > 0) { // to가 행 번호가 더 작다
                    return Movement.LEFT_UP;
                }
            }
        }
        throw new IllegalStateException("[ERROR] 직선 혹은 대각선 이동으로 갈 수 없는 위치입니다.");
    }

    private boolean isDiagonallyAligned(final Position other) {
        return Math.abs(this.row.difference(other.row)) == Math.abs(this.column.difference(other.column));
    }

    public boolean isPalace() {
        return palace.contains(this);
    }

    public boolean isPalaceSide() {
        return palaceSide.contains(this);
    }

    public int getRowValue() {
        return row.getValue();
    }

    public int getColumnValue() {
        return column.getValue();
    }
}
