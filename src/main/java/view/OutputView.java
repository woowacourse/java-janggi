package view;

import domain.JanggiBoard;
import domain.piece.Cannon;
import domain.piece.Car;
import domain.piece.Elephant;
import domain.piece.Guard;
import domain.piece.Horse;
import domain.piece.King;
import domain.piece.Pawn;
import domain.piece.Piece;
import domain.position.Position;

public class OutputView {
    private static final int BOARD_ROWS = 10;
    private static final int BOARD_COLUMNS = 9;

    public void printBoard(JanggiBoard board) {
        printColumnIndices();
        for (int row = 0; row < BOARD_ROWS; row++) {
            System.out.printf("%2d ", row);
            printRow(board, row);
            System.out.println();
        }
    }

    private void printColumnIndices() {
        System.out.print("   ");
        for (int col = 0; col < BOARD_COLUMNS; col++) {
            System.out.print(col + "．");
        }
        System.out.println();
    }

    private void printRow(JanggiBoard board, int row) {
        for (int col = 0; col < BOARD_COLUMNS; col++) {
            Position position = new Position(row, col);
            Piece piece = board.getPiece(position);
            System.out.print(getSymbol(piece) + " ");
        }
    }

    public void printErrorMessage(IllegalArgumentException e) {
        System.out.println(e.getMessage());
    }


    private String getSymbol(Piece piece) {
        if (piece instanceof Cannon) {
            return "포";
        }
        if (piece instanceof Car) {
            return "차";
        }
        if (piece instanceof Elephant) {
            return "상";
        }
        if (piece instanceof Guard) {
            return "사";
        }
        if (piece instanceof Horse) {
            return "마";
        }
        if (piece instanceof King) {
            return "궁";
        }
        if (piece instanceof Pawn) {
            return "졸";
        }
        return "．";
    }
}
