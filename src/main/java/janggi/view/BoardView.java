package janggi.view;

import janggi.board.Board;
import janggi.piece.Piece;
import janggi.piece.Type;
import janggi.position.Column;
import janggi.position.Position;
import janggi.position.Row;
import java.util.Map;

public final class BoardView {

    private static final Map<Type, String> PIECE_NOTATION_KOREAN = Map.of(
            Type.GENERAL, "장",
            Type.CHARIOT, "차",
            Type.CANNON, "포",
            Type.HORSE, "마",
            Type.ELEPHANT, "상",
            Type.GUARD, "사",
            Type.SOLDIER, "병"
    );

    public void display(final Board board) {
        System.out.println("\n  0＿1＿2＿3＿4＿5＿6＿7＿8");
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
            System.out.print("＿ ");
            return;
        }
        displayPiece(board.getPiece(position));
    }

    private static void displayPiece(final Piece piece) {
        final String notation = PIECE_NOTATION_KOREAN.get(piece.type());
        if (piece.isHan()) {
            System.out.print("\u001B[31m" + notation + " \u001B[0m");
            return;
        }
        System.out.print("\u001B[34m" + notation + " \u001B[0m");
    }
}
