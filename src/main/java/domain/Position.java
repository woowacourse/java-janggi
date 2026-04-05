package domain;

import java.util.Objects;

public class Position {
    public static final int MAX_ROW=10;
    public static final int MAX_COL=9;
    public static final int MIN_ROW_COL=1;

    private final int x;
    private final int y;

    private Position(int x, int y) {
        validateBoardSize(x, y);
        this.x = x;
        this.y = y;
    }

    public static Position create(int x, int y) {
        return new Position(x, y);
    }

    private void validateBoardSize(int x, int y){
        if (x < MIN_ROW_COL || x > MAX_ROW || y < MIN_ROW_COL || y > MAX_COL) {
            throw new IllegalArgumentException("좌표 범위를 벗어났습니다.");
        }
    }

    private static void validateInPalace(int x, int y){
        if (x < MIN_ROW_COL || x > MAX_ROW || y < MIN_ROW_COL || y > MAX_COL) {
            throw new IllegalArgumentException("궁성 좌표 범위를 벗어났습니다.");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
