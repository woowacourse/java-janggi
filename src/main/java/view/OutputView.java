package view;

import static constant.JanggiConstant.BOARD_MAX_HEIGHT;
import static constant.JanggiConstant.BOARD_MAX_WIDTH;
import static constant.JanggiConstant.BOARD_MIN_HEIGHT;
import static constant.JanggiConstant.BOARD_MIN_WIDTH;

import coordinate.Coordinate;
import java.util.Map;
import piece.Piece;

public class OutputView {

    private static final String FULL_WIDTH_BAR = "＿";
    private static final String FULL_WIDTH_SPACE = "　";
    private static final String COLUMN_HEADER = "   １　２　３　４　５　６　７　８　９";

    public void printBoard(Map<Coordinate, Piece> board) {
        println(COLUMN_HEADER);
        for (int y = BOARD_MIN_HEIGHT; y <= BOARD_MAX_HEIGHT; y++) {
            printf("%2d ", y);
            for (int x = BOARD_MIN_WIDTH; x <= BOARD_MAX_WIDTH; x++) {
                Coordinate coordinate = new Coordinate(x, y);
                Piece piece = board.get(coordinate);
                printPiece(piece);
            }
            printNewLine();
        }
        printNewLine();
    }

    private void printPiece(Piece piece) {
        if (piece == null) {
            print(FULL_WIDTH_BAR + FULL_WIDTH_SPACE);
            return;
        }
        print(piece.colorName() + FULL_WIDTH_SPACE);
    }

    private void print(String message) {
        System.out.print(message);
    }

    private void println(String message) {
        System.out.println(message);
    }

    private void printf(String format, Object... args) {
        System.out.printf(format, args);
    }

    private void printNewLine() {
        System.out.println();
    }
}
