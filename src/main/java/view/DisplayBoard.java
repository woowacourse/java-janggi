package view;

import board.Board;
import java.util.Map;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;
import position.Column;
import position.Position;
import position.Row;

public class DisplayBoard {

    private static final int MAX_ROW = 9;
    private static final int MAX_COLUMN = 8;
    private static final String EMPTY_SYMBOL = "..";

    private static final Map<PieceType, String> PIECE_SYMBOLS = Map.of(
        PieceType.CHA, "CH",
        PieceType.MA, "MA",
        PieceType.SANG, "SG",
        PieceType.SA, "SA",
        PieceType.GUNG, "GU",
        PieceType.PO, "PO",
        PieceType.JOL_BYEONG, "JB"
    );

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";

    private DisplayBoard() {
    }

    public static String of(Board board) {
        Map<Position, Piece> pieces = board.pieces();
        StringBuilder sb = new StringBuilder();

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

    private static void appendColumnHeader(StringBuilder sb) {
        sb.append("   ");
        for (int column = 0; column <= MAX_COLUMN; column++) {
            sb.append(" ")
                .append(padLeft(String.valueOf(column), 2))
                .append("  ");
        }
        sb.append(System.lineSeparator());
    }

    private static String cell(Piece piece) {
        if (piece == null) {
            return " " + EMPTY_SYMBOL + " ";
        }
        String symbol = symbolOf(piece);
        return " " + colorize(piece, symbol) + " ";
    }

    private static String colorize(Piece piece, String symbol) {
        if (piece.isSameSide(Side.HAN)) {
            return ANSI_RED + symbol + ANSI_RESET;
        }
        if (piece.isSameSide(Side.CHO)) {
            return ANSI_GREEN + symbol + ANSI_RESET;
        }
        return symbol;
    }

    private static String symbolOf(Piece piece) {
        String symbol = PIECE_SYMBOLS.get(piece.type());
        if (symbol == null) {
            throw new IllegalArgumentException("출력할 수 없는 기물 타입입니다. type=" + piece.type());
        }
        return symbol;
    }

    private static String padLeft(String value, int size) {
        if (value.length() >= size) {
            return value;
        }
        return " ".repeat(size - value.length()) + value;
    }
}