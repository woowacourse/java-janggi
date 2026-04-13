package janggi.view;

import janggi.domain.Board;
import janggi.domain.Position;
import janggi.domain.Space;
import janggi.domain.Team;
import java.util.List;
import java.util.stream.Collectors;

public class OutputView {

    private static String TEAM_SCORE = "%s나라: %.1f점";

    public static void printGameIds(List<Long> gameIds) {
        String result = gameIds.stream()
            .map(String::valueOf)
            .collect(Collectors.joining(", "));

        System.out.println("현재 진행 중인 게임 ID: " + result);
    }

    public static void printGameId(long gameId) {
        System.out.printf("id = %d 게임을 실행합니다.", gameId);
        printLine();
    }

    public static void printBoard(Board board) {
        printHeader(board);
        for (int y = 0; y < board.getBoardHeight(); y++) {
            printRow(board, y);
        }
    }

    public static void printTeamScore(Board board) {
        System.out.printf(TEAM_SCORE, Team.CHO.getName(), board.getChoScore());
        printLine();
        System.out.printf(TEAM_SCORE, Team.HAN.getName(), board.getHanScore());
        printLine();
    }

    private static void printHeader(Board board) {
        System.out.print("   ");
        for (int x = 0; x < board.getBoardWidth(); x++) {
            System.out.print(x + " ");
        }
        printLine();
    }

    private static void printRow(Board board, int y) {
        System.out.print(y + ": ");
        for (int x = 0; x < board.getBoardWidth(); x++) {
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
