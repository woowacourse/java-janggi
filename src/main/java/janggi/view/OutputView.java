package janggi.view;

import janggi.domain.Turn;
import janggi.domain.board.JanggiBoard;
import janggi.domain.board.Position;
import janggi.domain.piece.Piece;
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
                    System.out.print(ANSI_GREEN + piece.getType().getSymbol() + ANSI_RESET + "  ");
                    continue;
                }
                if (piece.isHan()) {
                    System.out.print(ANSI_RED + piece.getType().getSymbol() + ANSI_RESET + "  ");
                    continue;
                }
                System.out.print(piece.getType().getSymbol() + "  ");
            }
            System.out.println();
        }
        System.out.println("   ────────────────────────────");
        System.out.println("      a  b  c  d  e  f  g  h  i");
        System.out.println("\n G: 궁, S: 사, C: 차, P: 포, M: 마, E: 상, J: 졸(병) \n");
    }

    public void printTurn(final Turn turn) {
        System.out.println(turn.getName() + "의 차례입니다.");
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
            System.out.println(ANSI_GREEN + piece.getType().getSymbol() + ANSI_RESET + "를 잡았습니다.");
            return;
        }
        if (piece.isHan()) {
            System.out.println(ANSI_RED + piece.getType().getSymbol() + ANSI_RESET + "를 잡았습니다.");
        }
    }

    public void printEndMessage(final Turn turn, final Piece piece) {
        System.out.println(turn.getName() + "가 " + turn.getEnemySide().getName() + "의 "
                + piece.getType().getSymbol() + "을 잡아 게임을 종료합니다.\n" + turn.getName() + "의 승리입니다.");
    }

    public void printExceptionMessage(final Exception e) {
        System.out.println(e.getMessage());
    }

    public void printTotalScores(final int choTotalScore, final int hanTotalScore) {
        System.out.println("초나라의 점수는 " + choTotalScore + "입니다.");
        System.out.println("한나라의 점수는 " + hanTotalScore + "입니다.");
    }

    public void printAlreadyEnded() {
        System.out.println("이미 게임이 종료되었습니다.");
    }
}
