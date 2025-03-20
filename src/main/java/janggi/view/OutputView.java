package janggi.view;

import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.piece.Piece;
import janggi.view.util.PositionFormatter;

import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";

    public void printBoard(JanggiBoard janggiBoard) {
        Map<Position, Piece> board = janggiBoard.getBoard();
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
        System.out.println("\n G: 궁, S: 사, C: 차, P: 포, M: 마, E: 상, J: 졸(병) \n");
    }

    public void printReachableDestinations(final List<Position> positions) {
        System.out.print("해당 기물은 ");
        for (Position position : positions) {
            System.out.print(PositionFormatter.formatPositionToString(position) + " ");
        }
        System.out.println("로 이동 가능합니다.");
    }

    public void printMoveResult(final Piece piece) {
        if (piece.isCho()) {
            System.out.print(ANSI_GREEN + piece.getSymbol() + ANSI_RESET + "  ");
            return;
        }
        if (piece.isHan()) {
            System.out.print(ANSI_RED + piece.getSymbol() + ANSI_RESET + "  ");
        }
    }

    public void printExceptionMessage(final Exception e) {
        System.out.println(e.getMessage());
    }
}
