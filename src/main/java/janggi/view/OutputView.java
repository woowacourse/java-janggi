package janggi.view;

import janggi.board.Position;
import janggi.piece.Piece;

import java.util.Map;

public class OutputView {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";

    public void printBoard(Map<Position, Piece> board) {
        for (int y = 0; y < 10; y++) {
            System.out.print(y + "  |  ");
            for (int x = 0; x < 9; x++) {
                Piece piece = board.get(new Position(x, y));
                if (piece.isCho()) {
                    System.out.print(ANSI_GREEN + piece.getSymbol() + ANSI_RESET + "  ");
                    continue;
                }
                if (piece.isHan()) {
                    System.out.print(ANSI_RED + piece.getSymbol() + ANSI_RESET + "  ");
                    continue;
                }
                System.out.print(piece.getSymbol() + "  ");
            }
            System.out.println();
        }
        System.out.println("   ────────────────────────────");
        System.out.println("      a  b  c  d  e  f  g  h  i");
    }
}
