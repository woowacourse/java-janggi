package janggi.domain;

import janggi.exception.position.InvalidPositionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Position {
    private static final Map<String, Position> CACHE = new HashMap<>();

    static {
        Row.values().stream()
                .flatMap(row -> Column.values().stream()
                        .map(column -> new Position(row, column)))
                .forEach(position -> CACHE.put(generateKey(position.row, position.column), position));
    }

    private final Row row;
    private final Column column;

    private Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(Row row, Column column) {
        String key = generateKey(row, column);
        Position position = CACHE.get(key);
        if (position == null) {
            throw new InvalidPositionException();
        }
        return position;
    }

    public static Position from(List<Integer> coordinates) {
        return of(Row.of(coordinates.get(0)), Column.of(coordinates.get(1)));
    }

    public boolean isSameRow(Position to) {
        return this.getRowValue() == to.getRowValue();
    }

    public boolean isSameColumn(Position to) {
        return this.getColumnValue() == to.getColumnValue();
    }

    public boolean isInPalace() {
        return (getRowValue() >= 3 && getRowValue() <= 5 && getColumnValue() >= 0 && getColumnValue() <= 2) ||
                (getRowValue() >= 3 && getRowValue() <= 5 && getColumnValue() >= 7 && getColumnValue() <= 9);
    }

    public boolean isOnSameDiagonal(Position to) {
        return Math.abs(this.getRowValue() - to.getRowValue()) == Math.abs(this.getColumnValue() - to.getColumnValue());
    }

    public List<Position> getStraightPathTo(Position to) {
        if (isSameRow(to)) {
            return getRowStraightPositions(to);
        }

        return getColumnStraightPositions(to);
    }

    public int calculateDistance(Position to) {
        return Math.abs(row.getValue() - to.getRowValue());
    }

    public Position getMiddlePosition(Position to) {
        int middleRow = (row.getValue() + to.getRowValue()) / 2;
        int middleCol = (column.getValue() + to.getColumnValue()) / 2;

        return Position.of(Row.of(middleRow), Column.of(middleCol));
    }

    public boolean isInOwnPalace(Team team) {
        int row = getRowValue();
        int col = getColumnValue();

        if (row < 3 || row > 5) {
            return false;
        }

        if (team == Team.HAN) {
            return col >= 0 && col <= 2;
        }

        if (team == Team.CHO) {
            return col >= 7 && col <= 9;
        }

        return false;
    }

    public boolean isPalaceCenter() {
        int row = getRowValue();
        int col = getColumnValue();

        return row == 4 && (col == 1 || col == 8);
    }

    private boolean isPalaceCorner() {
        int col = getColumnValue();
        int row = getRowValue();

        boolean isHanCorner = (row == 3 || row == 5) && (col == 0 || col == 2);
        boolean isChoCorner = (row == 3 || row == 5) && (col == 7 || col == 9);

        return isHanCorner || isChoCorner;
    }

    public boolean isInEnemyPalace(Team team) {
        Team enemyTeam = (team == Team.HAN) ? Team.CHO : Team.HAN;
        return isInOwnPalace(enemyTeam);
    }

    public boolean isPalaceDiagonalPath(Position to) {
        return (this.isPalaceCorner() && to.isPalaceCenter()) ||
                (this.isPalaceCenter() && to.isPalaceCorner());
    }

    public int getRowValue() {
        return row.getValue();
    }

    public int getColumnValue() {
        return column.getValue();
    }

    private static String generateKey(Row row, Column column) {
        return String.format("%d,%d", row.getValue(), column.getValue());
    }

    private List<Position> getRowStraightPositions(Position to) {
        List<Position> path = new ArrayList<>();

        int start = Math.min(this.getRowValue(), to.getRowValue()) + 1;
        int end = Math.max(this.getRowValue(), to.getRowValue());

        for (int i = start; i < end; i++) {
            path.add(Position.of(Row.of(i), Column.of(this.getColumnValue())));
        }

        return path;
    }

    private List<Position> getColumnStraightPositions(Position to) {
        List<Position> path = new ArrayList<>();

        int start = Math.min(this.getColumnValue(), to.getColumnValue()) + 1;
        int end = Math.max(this.getColumnValue(), to.getColumnValue());

        for (int i = start; i < end; i++) {
            path.add(Position.of(Row.of(this.getRowValue()), Column.of(i)));
        }

        return path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Position position = (Position) o;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
