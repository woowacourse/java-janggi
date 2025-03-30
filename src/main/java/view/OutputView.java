package view;

import domain.Board;
import domain.Team;
import domain.piece.Piece;
import domain.piece.PieceType;
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
            printLine(board, i);
        }
    }

    private static void printLine(final Board board, final int i) {
        for (int j = 0; j <= 8; j++) {
            printPoint(board, j, i);
        }
        System.out.println();
    }

    private static void printPoint(final Board board, final int j, final int i) {
        final Position target;
        try {
            target = board.findPositionBy(Point.newInstance(j, i));
            printPiecePoint(j, i, target);
        } catch (final IllegalArgumentException e) {
            System.out.printf("%-1s\t", "X" + "(" + j + "," + i + ")");
        }
    }

    private static void printPiecePoint(final int j, final int i, final Position target) {
        final Piece piece = target.getPiece();
        final PieceType pieceType = piece.type();
        if (piece.isGreenTeam()) {
            System.out.printf("%-1s\t",
                    GREEN + SCORE_RB.getString(pieceType.name()) + "(" + j + "," + i + ")" + RESET);
            return;
        }
        System.out.printf("%-1s\t",
                RED + SCORE_RB.getString(pieceType.name()) + "(" + j + "," + i + ")" + RESET);
    }

    public static void printWinnerTeam(final Team team, final double winnerScore, final double loserScore) {
        System.out.println(TEAM_RB.getString(team.name()) + "가 승리했습니다.");
        System.out.println(TEAM_RB.getString(team.name()) + "의 점수: " + winnerScore);
        System.out.println(TEAM_RB.getString(team.opposite().name()) + "의 점수: " + loserScore);
    }

    public static void printCaptureMessage() {
        System.out.println("상대팀 말을 잡았습니다.\n");
    }

    public static void printInvalidFromPoint() {
        System.out.println("우리팀 말이 아닙니다. 턴이 종료되었습니다.\n");
    }

    public static void printInvalidEndPoint() {
        System.out.println("말이 이동할 수 없는 위치입니다. 턴이 종료되었습니다.");
    }

    public static void printEndTurn() {
        System.out.println("턴이 종료되었습니다.");
    }
}
