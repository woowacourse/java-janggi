package domain;

public class 마 extends Piece {
    public 마(Side side) {
        super(5, side);
    }

    @Override
    public boolean canMove(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        return (Math.abs(afterRow - beforeRow) == 2 && Math.abs(afterColumn - beforeColumn) == 1) || (
                Math.abs(afterRow - beforeRow) == 1 && Math.abs(afterColumn - beforeColumn) == 2);
    }
}
