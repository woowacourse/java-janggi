package domain.piece;

public class Position {

    private final int row;
    private final int column;

    public Position(final int row, final int column) {
        this.row = row;
        this.column = column;

        if (row < 1 || column < 1 || row > 10 || column > 9) {
            throw new IllegalArgumentException("[ERROR] 좌표 입력은 9X10 보드 이내만 가능합니다.");
        }
    }
}
