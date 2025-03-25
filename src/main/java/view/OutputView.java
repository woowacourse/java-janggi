package view;

import janggiGame.board.Board;
import janggiGame.board.Dot;
import janggiGame.piece.Dynasty;
import janggiGame.piece.Piece;
import janggiGame.piece.Type;
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
                System.out.print("\u001B[32m" + " " + getName(piece) + "\u001B[0m");
                continue;
            }

            System.out.print("\u001B[31m" + " " + getName(piece) + "\u001B[0m");
        }
        System.out.println();

        System.out.println("  ０ １ ２ ３ ４ ５ ６ ７ 8");
    }

    private String getName(Piece piece) {
        Type type = piece.getType();

        switch (type) {
            case KING -> {
                return "장";
            }
            case PAWN -> {
                return "병";
            }
            case HORSE -> {
                return "마";
            }
            case CANNON -> {
                return "포";
            }
            case ADVISOR -> {
                return "사";
            }
            case CHARIOT -> {
                return "차";
            }
            case ELEPHANT -> {
                return "상";
            }
            case null, default -> throw new RuntimeException();
        }
    }
}
