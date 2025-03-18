package domain;

public class 포 extends Piece {
    private int hurdleRow;
    private int hurdleColumn;

    public 포(Side side) {
        super(7, side);
    }

    public void setHurdle(int hurdleRow, int hurdleColumn) {
        this.hurdleRow = hurdleRow;
        this.hurdleColumn = hurdleColumn;
    }

    @Override
    public boolean canMove(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        if ((beforeRow - hurdleRow) * (hurdleRow - afterRow) > 0 && beforeColumn == afterColumn) {
            return true;
        }

        if ((beforeColumn - hurdleColumn) * (hurdleColumn - afterColumn) > 0 && beforeRow == afterRow) {
            return true;
        }
        return false;
    }
}
