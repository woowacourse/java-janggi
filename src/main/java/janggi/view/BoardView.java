package janggi.view;

import janggi.domain.game.Game;
import janggi.domain.game.Team;
import janggi.domain.piece.Piece;
import janggi.domain.piece.Type;
import janggi.domain.position.Column;
import janggi.domain.position.Position;
import janggi.domain.position.Row;
import java.util.Map;

public final class BoardView {

    private static final Map<Type, String> pieceNotations = Map.of(
            Type.GENERAL, "k",
            Type.CHARIOT, "c",
            Type.CANNON, "p",
            Type.HORSE, "h",
            Type.ELEPHANT, "e",
            Type.GUARD, "g",
            Type.SOLDIER, "s");

    public void displayBoard(final Game game) {
        System.out.printf("%s나라의 턴입니다.", game.getTurn().getName());
        System.out.println("\n  012345678");
        for (Row row : Row.values()) {
            displayRow(game, row);
        }
    }

    private void displayRow(final Game game, final Row row) {
        System.out.printf("%d ", row.getValue());
        for (Column column : Column.values()) {
            Position position = new Position(column, row);
            displayPosition(game, position);
        }
        System.out.println();
    }

    private static void displayPosition(final Game game, final Position position) {
        if (!game.hasPieceAt(position)) {
            System.out.print(".");
            return;
        }
        displayPiece(game.getPieceAt(position));
    }

    private static void displayPiece(final Piece piece) {
        final String notation = pieceNotations.get(piece.type());
        if (piece.team() == Team.HAN) {
            System.out.print(notation);
            return;
        }
        System.out.print(notation.toUpperCase());
    }
}
