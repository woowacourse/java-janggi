package janggi.view;

import janggi.PieceSymbol;
import janggi.Point;
import janggi.piece.Piece;
import java.util.Map;

public class View {

    private static final int ROW = 10;
    private static final int COLUMN = 9;
    private static final String EMPTY_SPACE = "ㅁ";

    public void displayBoard(Map<Point, Piece> placedPieces) {
        for (int i = ROW; i >= 1; i--) {
            for (int j = COLUMN; j >= 1; j--) {
                if (placedPieces.get(new Point(j, i)) == null) {
                    System.out.print(EMPTY_SPACE);
                    continue;
                }
                System.out.print(formatPiece(placedPieces.get(new Point(j, i))));
            }
            System.out.println();
        }
    }

    private String formatPiece(Piece piece) {
        PieceSymbol pieceSymbol = piece.getPieceSymbol();
        return pieceSymbol.getDisplayAttributes(piece.getCamp());
    }
}
