package view;

import janggiGame.Position;
import janggiGame.piece.character.Dynasty;
import janggiGame.piece.Piece;
import java.util.Map;
import java.util.ResourceBundle;

public class OutputView {
    private static final ResourceBundle pieceTypeBundle = ResourceBundle.getBundle("pieceType");
    private static final ResourceBundle dynastyBundle = ResourceBundle.getBundle("dynasty");
    private static final String BLANK = "＿";

    public void printBoard(Map<Position, Piece> pieces) {
        for (Position position : Position.getDots()) {
            if (position.getRow() == 0) {
                System.out.println();
                System.out.printf("%d", position.getColumn());
            }

            if (!pieces.containsKey(position)) {
                System.out.printf("%2s", BLANK);
                continue;
            }

            Piece piece = pieces.get(position);

            if (piece.hasDynasty(Dynasty.CHO)) {
                System.out.print("\u001B[32m" + " " + pieceTypeBundle.getString(piece.getType().name()) + "\u001B[0m");
                continue;
            }

            System.out.print("\u001B[31m" + " " + pieceTypeBundle.getString(piece.getType().name()) + "\u001B[0m");
        }
        System.out.println();

        System.out.println("  ０ １ ２ ３ ４ ５ ６ ７ 8");
    }

    public void printScore(double hanScore, double choScore) {
        System.out.printf("한나라의 점수: %.1f \n초나라의 점수: %.1f\n", hanScore, choScore);
    }

    public void printWinner(Dynasty dynasty) {
        System.out.printf("우승한 나라는 %s입니다.\n",dynastyBundle.getString(dynasty.name()));
    }
}
