package domain;

public abstract class Piece {
    protected int score;
    protected Side side;

    public Piece(int score, Side side) {
        this.score = score;
        this.side = side;
    }

    public abstract boolean canMove(int beforeRow, int beforeColumn, int afterRow, int afterColumn);
}
