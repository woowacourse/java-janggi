package janggi.view;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Space;

public class OutputView {

    public static void printBoard(Board board) {
        printHeader(board);
        for (int y = 0; y < board.getBoardWidth(); y++) {
            printRow(board, y);
        }
        System.out.println();
    }

    private static void printHeader(Board board) {
        System.out.print("   ");
        for (int x = 0; x < board.getBoardHeight(); x++) {
            System.out.print(x + " ");
        }
        printLine();
    }

    private static void printRow(Board board, int y) {
        System.out.print(y + ": ");
        for (int x = 0; x < board.getBoardHeight(); x++) {
            Space space = board.getSpace(new Position(x, y));
            System.out.print(formatSpace(space) + " ");
        }
        printLine();
    }

    private static String formatSpace(Space space) {
        return space.displayValue();
    }

    private static void printLine() {
        System.out.println();
    }
}
