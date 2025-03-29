package janggi.view;

import janggi.temp.game.Game;
import janggi.temp.game.Team;
import janggi.temp.piece.Piece;
import janggi.temp.piece.Type;
import janggi.temp.position.Column;
import janggi.temp.position.Position;
import janggi.temp.position.Row;
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

    public void displaySetUp(final Game game) {
        System.out.println("\n게임을 시작합니다...");
        System.out.println("  012345678");
        for (Row row : Row.values()) {
            displayRow(game, row);
        }
    }

    public void displayBoard(final Game game) {
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
