package domain;

public class 차 extends Piece {
    public 차(Side side) {
        super(13, side);
    }

    @Override
    public boolean canMove(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        return beforeRow == afterRow || beforeColumn == afterColumn;
    }
}
