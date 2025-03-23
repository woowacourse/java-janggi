package view;

import janggiGame.Dot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import java.util.Map;
import java.util.ResourceBundle;

public class OutputView {
    private static final ResourceBundle pieceTypeBundle = ResourceBundle.getBundle("pieceType");
    private static final String BLANK = "＿";

    public void printBoard(Map<Dot, Piece> pieces) {
        for (Dot dot : Dot.getDots()) {
            if (dot.getRow() == 0) {
                System.out.println();
                System.out.printf("%d", dot.getColumn());
            }

            if (!pieces.containsKey(dot)) {
                System.out.printf("%2s", BLANK);
                continue;
            }

            Piece piece = pieces.get(dot);

            if (piece.getDynasty() == Dynasty.CHO) {
                System.out.print("\u001B[32m" + " " + pieceTypeBundle.getString(piece.getType().name()) + "\u001B[0m");
                continue;
            }

            System.out.print("\u001B[31m" + " " + pieceTypeBundle.getString(piece.getType().name()) + "\u001B[0m");
        }
        System.out.println();

        System.out.println("  ０ １ ２ ３ ４ ５ ６ ７ 8");
    }
}
