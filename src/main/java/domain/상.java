package domain;

public class 상 extends Piece {
    public 상(Side side) {
        super(3, side);
    }

    @Override
    public boolean canMove(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        return (Math.abs(afterRow - beforeRow) == 2 && Math.abs(afterColumn - beforeColumn) == 3) || (
                Math.abs(afterRow - beforeRow) == 3 && Math.abs(afterColumn - beforeColumn) == 2);
    }
}
