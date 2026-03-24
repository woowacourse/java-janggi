package model;

public record Position(Row row, Column column) {
    public static Position of(int x, int y) {
        return new Position(Row.from(x), Column.from(y));
    }
}
