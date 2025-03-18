package domain;

public class Board {

    private final Piece[][] board;

    public Board() {
        this.board = new Piece[10][9];
    }

    public Piece[][] getBoard() {
        return board;
    }

    public void putPiece(Piece piece) {
        Position position = piece.getPosition();
        board[position.getY()][position.getX()] = piece;
    }
}
