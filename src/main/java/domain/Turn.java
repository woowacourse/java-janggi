package domain;

public class Turn {

    private final Board board;
    private int turn = 0;

    public Turn(Board board) {
        this.board = board;
    }

    public void process(BoardLocation current, BoardLocation destination) {
        if (turn % 2 == 0) {
            board.moveHanPiece(current, destination);
            turn++;
            return;
        }
        board.moveChoPiece(current, destination);
        turn++;
    }
}
