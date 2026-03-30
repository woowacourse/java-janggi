package view;

import domain.JanggiBoard;
import domain.position.Position;
import domain.piece.Piece;

public class OutputView {
    private static final int BOARD_ROWS = 10;
    private static final int BOARD_COLUMNS = 9;

    public void printBoard(JanggiBoard board) {
        for (int row = 0; row < BOARD_ROWS; row++) {
            printRow(board, row);
            System.out.println(); // 한 행이 끝나면 줄바꿈
        }
    }

    private void printRow(JanggiBoard board, int row) {
        for (int col = 0; col < BOARD_COLUMNS; col++) {
            Position position = new Position(row, col);
            Piece piece = board.getPiece(position);
            System.out.print(getSymbol(piece) + " ");
        }
    }

    private String getSymbol(Piece piece) {
        return "차";
    }
}
