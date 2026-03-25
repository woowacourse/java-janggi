package view;

import domain.piece.Piece;
import view.message.PieceView;

public class OutputView {

    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";

    public void printBoard(Piece[][] board) {
        System.out.println("   0  1   2  3   4   5  6   7  8");

        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 9; j++) {
                printPieceBySide(board[i][j]);
            }
            System.out.println();
        }
    }

    private void printPieceBySide(Piece piece) {
        if (piece.isHan()) {
            System.out.print(" " + RED + PieceView.from(piece) + RESET + " ");
            return;
        }

        if (piece.isChu()) {
            System.out.print(" " + BLUE + PieceView.from(piece) + RESET + " ");
            return;
        }

        System.out.print(" " + PieceView.from(piece) + " ");
    }

    public static void printErrorMessage(String message) {
        System.out.println(message);
    }
}
