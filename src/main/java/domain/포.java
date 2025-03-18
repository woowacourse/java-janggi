package domain;

public class 포 extends Piece {
    public 포() {
    }

    public boolean canMove(int hurdleRow, int hurdleColumn, int beforeRow, int beforeColumn, int afterRow,
                           int afterColumn) {
        if ((beforeRow - hurdleRow) * (hurdleRow - afterRow) > 0 && beforeColumn == afterColumn) {
            return true;
        }

        if ((beforeColumn - hurdleColumn) * (hurdleColumn - afterColumn) > 0 && beforeRow == afterRow) {
            return true;
        }
        return false;
    }
}
