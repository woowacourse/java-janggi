package view;

import game.Board;
import game.Dot;
import piece.Dynasty;
import piece.Piece;

import java.util.Map;

public class OutputView {
    private static final String BLANK = "＿";

    public void printBoard(Map<Dot, Piece> pieces) {
        for (Dot dot : Board.getDots()) {
            if (dot.getX() == 0) {
                System.out.println();
                System.out.printf("%d", dot.getY());
            }

            if (!pieces.containsKey(dot)) {
                System.out.printf("%2s", BLANK);
                continue;
            }

            Piece piece = pieces.get(dot);

            if (piece.getDynasty() == Dynasty.CHO) {
                System.out.print("\u001B[32m" + " " + piece.getName() + "\u001B[0m");
                continue;
            }

            System.out.print("\u001B[31m" + " " + piece.getName() + "\u001B[0m");
        }
        System.out.println();

        System.out.println("  ０ １ ２ ３ ４ ５ ６ ７ 8");
    }
}
