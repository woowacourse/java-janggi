package domain;

public class JanggiBoard {

    private final Piece[][] board = new Piece[9][10];

    public Piece[][] getBoard() {
        return board;
    }
}
