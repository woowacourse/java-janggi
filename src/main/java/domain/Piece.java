package domain;

public abstract class Piece {

    public Piece() {
    }

    public abstract boolean canMove(int beforeRow, int beforeColumn, int afterRow, int afterColumn);
}
