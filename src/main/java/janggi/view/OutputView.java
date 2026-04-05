package janggi.view;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Space;
import janggi.domain.Team;

public class OutputView {

    private static String TEAM_SCORE = "%s나라: %d점";

    public static void printBoard(Board board) {
        printHeader(board);
        for (int y = 0; y < board.getBoardWidth(); y++) {
            printRow(board, y);
        }
    }

    public static void printTeamScore(Board board) {
        System.out.printf(TEAM_SCORE, Team.CHO.name(), board.getChoScore());
        printLine();
        System.out.printf(TEAM_SCORE, Team.HAN.name(), board.getHanScore());
        printLine();
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
