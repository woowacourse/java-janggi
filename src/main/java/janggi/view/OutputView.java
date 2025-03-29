package janggi.view;

import janggi.domain.Board;
import janggi.domain.Team;
import janggi.domain.Turn;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import java.util.Map;

public class OutputView {

    private static final String COLOR_CODE_EXIT = "\u001B[0m";
    private static final String COLOR_CODE_RED = "\u001B[31m";
    private static final String COLOR_CODE_YELLOW = "\u001B[33m";
    private static final String COLOR_CODE_GREEN = "\u001B[32m";

    public void printBoard(Board board) {
        System.out.println("청팀 점수: " + board.getScore(Team.BLUE).getValue());
        System.out.println("홍팀 점수: " + board.getScore(Team.RED).getValue());
        System.out.print("   ");
        for (int j = 1; j <= 9; j++) {
            System.out.printf("%4s", j + "");
        }
        System.out.println();
        Map<Position, Piece> pieces = board.getPieces();
        for (int i = 1; i <= 10; i++) {
            System.out.printf("%-3d", i);
            for (int j = 1; j <= 9; j++) {
                Piece piece = pieces.get(new Position(i, j));
                System.out.printf("%s%3s%s ", setPrintColorByTeam(piece), piece.getName(),
                    COLOR_CODE_EXIT);
            }
            System.out.println();
        }
    }

    private String setPrintColorByTeam(Piece piece) {
        if (piece.getTeam().equals(Team.BLUE)) {
            return COLOR_CODE_GREEN;
        }
        if (piece.getTeam().equals(Team.RED)) {
            return COLOR_CODE_RED;
        }
        return COLOR_CODE_YELLOW;
    }

    public void printWinner(Board board) {
        Team winner = board.getWinner();
        if (winner == Team.BLUE) {
            System.out.println("청팀 승리");
        }
        if (winner == Team.RED) {
            System.out.println("홍팀 승리");
        }
    }

    public void printTurn(Turn turn) {
        Team team = turn.getTeam();
        if (team == Team.BLUE) {
            System.out.println("청팀 차례입니다");
        }
        if (team == Team.RED) {
            System.out.println("홍팀 차례입니다");
        }
    }

    public void printSaved() {
        System.out.println("게임 저장됨");
    }
}
