package janggi.view;

import janggi.board.BoardStatus;
import janggi.board.JanggiBoard;
import janggi.board.Position;
import janggi.piece.Piece;
import janggi.piece.Side;
import janggi.view.util.PositionFormatter;
import java.util.List;
import java.util.Map;

public class OutputView {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_ORANGE = "\u001B[33m";

    public void printBoard(JanggiBoard janggiBoard) {
        Map<Position, Piece> board = janggiBoard.getBoard();
        for (int y = 0; y < 10; y++) {
            System.out.print(y + "  |  ");
            for (int x = 0; x < 9; x++) {
                Position position = new Position(x, y);
                Piece piece = board.get(position);
                if (piece.isCho()) {
                    System.out.print(ANSI_GREEN + piece.getSymbol().getSymbol() + ANSI_RESET + "  ");
                    continue;
                }
                if (piece.isHan()) {
                    System.out.print(ANSI_RED + piece.getSymbol().getSymbol() + ANSI_RESET + "  ");
                    continue;
                }
                if (position.isPalace()) {
                    System.out.print(ANSI_ORANGE + "ˣ" + ANSI_RESET + "  ");
                    continue;
                }
                System.out.print(piece.getSymbol().getSymbol() + "  ");
            }
            System.out.println();
        }
        System.out.println("   ────────────────────────────");
        System.out.println("      a  b  c  d  e  f  g  h  i");
        System.out.println("\n G: 궁, S: 사, C: 차, P: 포, M: 마, E: 상, J: 졸(병) \n");
    }

    public void printCurrentBoardStatus(JanggiBoard board) {
        BoardStatus status = board.getStatus();
        if (status.getSide() == Side.CHO) {
            System.out.println(ANSI_GREEN + status.getMessage() + ANSI_RESET + " : " + board.calculateScore(Side.CHO) + "점");
            return;
        }
        if (status.getSide() == Side.HAN) {
            System.out.println(ANSI_RED + status.getMessage() + ANSI_RESET + " : " + board.calculateScore(Side.HAN) + "점");
        }
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
            System.out.println(ANSI_GREEN + piece.getSymbol().getSymbol() + ANSI_RESET + " 를 잡았습니다.");
        }
        if (piece.isHan()) {
            System.out.println(ANSI_RED + piece.getSymbol().getSymbol() + ANSI_RESET + " 를 잡았습니다.");
        }
        System.out.println();
    }

    public void printCompetitionResult(final JanggiBoard board) {
        if (board.isGameEnd()) {
            printCurrentBoardStatus(board);
        }
    }

    public void printExceptionMessage(final Exception e) {
        System.out.println(e.getMessage());
    }

}
