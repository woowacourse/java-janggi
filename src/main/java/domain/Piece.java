package domain;

public abstract class Piece {
    protected int score;

    public Piece(int score) {
        this.score = score;
    }

    public abstract boolean canMove(int beforeRow, int beforeColumn, int afterRow, int afterColumn);
}
