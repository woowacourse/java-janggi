package view;

import board.Board;
import java.util.Map;
import pieces.Piece;
import position.Column;
import position.Position;
import position.Row;

public class DisplayBoard {

    private static final int MAX_ROW = 9;
    private static final int MAX_COLUMN = 8;
    private static final String EMPTY_SYMBOL = "..";

    private DisplayBoard() {
    }

    public static String of(final Board board) {
        final Map<Position, Piece> pieces = board.pieces();
        final StringBuilder sb = new StringBuilder();

        for (int row = MAX_ROW; row >= 0; row--) {
            sb.append(padLeft(String.valueOf(row), 2)).append(" |");

            for (int column = 0; column <= MAX_COLUMN; column++) {
                Position position = new Position(new Row(row), new Column(column));
                Piece piece = pieces.get(position);
                sb.append(cell(piece)).append("|");
            }
            sb.append(System.lineSeparator());
        }

        appendColumnHeader(sb);
        return sb.toString();
    }

    private static void appendColumnHeader(final StringBuilder sb) {
        sb.append("   ");
        for (int column = 0; column <= MAX_COLUMN; column++) {
            sb.append(" ")
                .append(padLeft(String.valueOf(column), 2))
                .append("  ");
        }
        sb.append(System.lineSeparator());
    }

    private static String cell(final Piece piece) {
        if (piece == null) {
            return " " + EMPTY_SYMBOL + " ";
        }
        return " " + DisplayPiece.symbolOf(piece) + " ";
    }

    private static String padLeft(final String value, final int size) {
        if (value.length() >= size) {
            return value;
        }
        return " ".repeat(size - value.length()) + value;
    }
}