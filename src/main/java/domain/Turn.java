package domain;

public class Turn {

    private final Board board;
    private int turn = 1;

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

    public Board getBoard() {
        return board;
    }

    public String getTurn() {
        if (turn % 2 == 0) {
            return "한나라";
        }
        return "초나라";
    }
}
