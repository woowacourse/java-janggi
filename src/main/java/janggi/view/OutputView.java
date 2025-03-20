package janggi.view;

import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import janggi.piece.Piece;
import java.util.Map;

public final class OutputView {
    public void displayBoard(final Map<Position, Piece> board) {
        System.out.println("\n  012345678");
        for (Row row : Row.values()) {
            displayRow(board, row);
        }
    }

    public void displayRow(final Map<Position, Piece> board, final Row row) {
        System.out.printf("%d ", row.getValue());
        for (Column column : Column.values()) {
            Position position = new Position(row, column);
            displayPosition(board, position);
        }
        System.out.println();
    }

    private static void displayPosition(final Map<Position, Piece> board, final Position position) {
        if (!board.containsKey(position)) {
            System.out.print(".");
            return;
        }
        displayPiece(board.get(position));
    }

    private static void displayPiece(final Piece piece) {
        final String notation = ViewConstant.pieceNotations.get(piece.type());
        if (piece.isHan()) {
            System.out.print(notation);
            return;
        }
        System.out.print(notation.toUpperCase());
    }
}
