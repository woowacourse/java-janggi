package view;

import domain.Board;
import domain.Score;
import domain.Team;
import domain.piece.Piece;
import domain.position.Point;
import domain.position.Position;
import java.util.ResourceBundle;

public final class OutputView {

    private static final ResourceBundle SCORE_RB = ResourceBundle.getBundle("score");
    private static final ResourceBundle TEAM_RB = ResourceBundle.getBundle("team");
    private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    private static final String RESET = "\033[0m";

    private OutputView() {
    }

    public static void printGreenTurn() {
        System.out.println("\n" + GREEN + "초나라" + RESET + " 차례입니다.");
    }

    public static void printRedTurn() {
        System.out.println("\n" + RED + "한나라" + RESET + " 차례입니다.");
    }

    public static void printBoard(final Board board) {
        for (int i = 9; i >= 0; i--) {
            for (int j = 0; j <= 8; j++) {
                final Position target;
                try {
                    target = board.findPositionBy(Point.of(j, i));
                    final Piece piece = target.getPiece();
                    final Score score = piece.getScore();
                    if (piece.isGreenTeam()) {
                        System.out.printf("%-1s\t",
                                GREEN + SCORE_RB.getString(score.name()) + "(" + j + "," + i + ")" + RESET);
                    } else {
                        System.out.printf("%-1s\t",
                                RED + SCORE_RB.getString(score.name()) + "(" + j + "," + i + ")" + RESET);
                    }

                } catch (final IllegalArgumentException e) {
                    System.out.printf("%-1s\t", "X" + "(" + j + "," + i + ")");
                }
            }
            System.out.println();
        }
    }

    public static void printWinnerTeam(final Team team) {
        System.out.println(TEAM_RB.getString(team.name()) + "가 승리했습니다.");
    }

    public static void printCaptureMessage() {
        System.out.println("상대팀 말을 잡았습니다.\n");
    }

    public static void printEndTurn() {
        System.out.println("우리팀 말이 아닙니다. 턴이 종료되었습니다.\n");
    }
}
