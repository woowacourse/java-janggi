package domain;

public class 궁 extends Piece {

    public 궁() {
    }

    public boolean canMove(int beforeRow, int beforeColumn, int afterRow, int afterColumn) {
        if (beforeRow == afterRow && Math.abs(beforeColumn - afterColumn) == 1) {
            return true;
        }
        return beforeColumn == afterColumn && (Math.abs(beforeRow - afterRow) == 1
                || Math.abs(beforeRow - afterRow) == 9);
    }
}
