package domain;

public class 차 extends Piece {
    public 차() {
    }

    @Override
    public boolean canMove(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        return beforeRow == afterRow || beforeColumn == afterColumn;
    }
}
