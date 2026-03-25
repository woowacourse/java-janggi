package view;

import model.Board;
import model.Country;
import model.Position;
import model.pieces.Piece;

public class OutputView {
    public static void printArrangeCountry(Country country) {
        System.out.printf("%s나라의 진영을 선택해주세요.%n", country.color() + country.title() + Country.RESET);
    }

    public static void printBoard(Board board) {
        for (int row = 1; row <= 10; row++) {
            printColumn(row, board);
            printLine();
        }
    }

    private static void printColumn(int row, Board board) {
        for (int col = 1; col <= 9; col++) {
            Position position = Position.of(row, col);
            Piece piece = board.findPiece(position);
            printMark(piece);
        }
    }

    private static void printMark(Piece piece) {
        if (piece == null) {
            System.out.print(".");
        }
        if (piece != null) {
            System.out.print(piece.mark(piece.country()));
        }
    }

    public static void printLine() {
        System.out.println();
    }
}
