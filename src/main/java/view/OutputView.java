package view;

import domain.Board;
import domain.Piece;
import domain.Point;
import domain.Position;
import domain.Score;
import java.util.ResourceBundle;

public final class OutputView {

    private static final ResourceBundle rb = ResourceBundle.getBundle("score");

    private OutputView() {
    }

    public static void printGreenTurn() {
        System.out.println("\n초나라 차례입니다.");
    }

    public static void printRedTurn() {
        System.out.println("한나라 차례입니다.");
    }

    public static void printBoard(final Board board) {
        for (int i = 9; i >= 0; i--) {
            for (int j = 0; j <= 8; j++) {
                final Position target;
                try {
                    target = board.findPositionBy(Point.of(j, i));
                    final Piece piece = target.getPiece();
                    final Score score = piece.getScore();

                    System.out.printf("%-1s\t", rb.getString(score.name()) + "(" + j + "," + i + ")");

                } catch (final IllegalArgumentException e) {
                    System.out.printf("%-1s\t", "X" + "(" + j + "," + i + ")");
                }
            }
            System.out.println();
        }
    }

    public static void printCaptureMessage() {
        System.out.println("상대팀 말을 잡았습니다.\n");
    }
}
