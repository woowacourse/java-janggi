package janggi.view;

import janggi.Column;
import janggi.Position;
import janggi.Row;
import janggi.piece.Piece;
import java.util.Map;

public final class OutputView {
    public void displayBoard(final Map<Position, Piece> board) {
        System.out.println("  012345678");
        for (Row row : Row.values()) {
            displayRow(board, row);
        }
    }

    public void displayRow(final Map<Position, Piece> board, final Row row) {
        System.out.printf("%d ", row.getValue());
        for (Column column : Column.values()) {
            Position position = new Position(row, column);
            if (board.containsKey(position)) {
                final Piece piece = board.get(position);
                if (piece.isHan()) {
                    final String notation = ViewConstant.pieceNotations.get(piece.getClass());
                    System.out.print(notation);
                }
                if (!piece.isHan()) {
                    final String notation = ViewConstant.pieceNotations.get(piece.getClass());
                    System.out.print(notation.toUpperCase());
                }
            }
            if (!board.containsKey(position)) {
                System.out.print(".");
            }
        }
        System.out.println();
    }
}
