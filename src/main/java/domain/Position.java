package domain;

import java.util.Objects;

public class Position {
    public static final int MAX_ROW=10;
    public static final int MAX_COL=9;
    public static final int MIN_ROW_COL=1;

    private final int x;
    private final int y;

    public Position(int x, int y) {
        validateBoardSize(x, y);
        this.x = x;
        this.y = y;
    }

    private void validateBoardSize(int x, int y){
        if (x<MIN_ROW_COL || x>MAX_ROW || y<MIN_ROW_COL || y>MAX_COL){
            throw new IndexOutOfBoundsException("좌표 범위를 벗어났습니다.");
        }
    }

    public Position getNextUpDownPosition(){
        return new Position(x+1, y);
    }

    public Position getNextLeftRightPosition(){
        return new Position(x, y+1);
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
