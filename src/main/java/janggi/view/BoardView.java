package janggi.view;

import janggi.board.Board;
import janggi.piece.Piece;
import janggi.piece.Type;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import java.util.Map;

public final class BoardView {

    public static final Map<Type, String> pieceNotations = Map.of(
            Type.GENERAL, "k",
            Type.CHARIOT, "c",
            Type.CANNON, "p",
            Type.HORSE, "h",
            Type.ELEPHANT, "e",
            Type.GUARD, "g",
            Type.SOLDIER, "s"
    );

    public void display(final Board board) {
        System.out.println("\n  012345678");
        for (Row row : Row.values()) {
            displayRow(board, row);
        }
    }

    private void displayRow(final Board board, final Row row) {
        System.out.printf("%d ", row.getValue());
        for (Column column : Column.values()) {
            Position position = new Position(row, column);
            displayPosition(board, position);
        }
        System.out.println();
    }

    private static void displayPosition(final Board board, final Position position) {
        if (!board.isExistPiece(position)) {
            System.out.print(".");
            return;
        }
        displayPiece(board.getPiece(position));
    }

    private static void displayPiece(final Piece piece) {
        final String notation = pieceNotations.get(piece.type());
        if (piece.isHan()) {
            System.out.print(notation);
            return;
        }
        System.out.print(notation.toUpperCase());
    }
}
