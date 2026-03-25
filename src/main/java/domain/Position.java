package domain;

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
}
