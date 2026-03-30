package view;

import board.Board;
import java.util.Map;
import pieces.FullPiece;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;
import position.Column;
import position.Position;
import position.Row;

public class DisplayBoard {

    private static final Map<PieceType, String> PIECE_SYMBOLS = Map.of(
        PieceType.EMPTY, "..",
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
        if (pieces.isEmpty()) {
            return "(empty board)";
        }

        int minRow = Integer.MAX_VALUE;
        int maxRow = Integer.MIN_VALUE;
        int minColumn = Integer.MAX_VALUE;
        int maxColumn = Integer.MIN_VALUE;

        for (Position position : pieces.keySet()) {
            int row = position.row().index();
            int column = position.column().index();

            minRow = Math.min(minRow, row);
            maxRow = Math.max(maxRow, row);
            minColumn = Math.min(minColumn, column);
            maxColumn = Math.max(maxColumn, column);
        }

        StringBuilder sb = new StringBuilder();

        for (int row = maxRow; row >= minRow; row--) {
            sb.append(padLeft(String.valueOf(row), 2)).append(" |");

            for (int column = minColumn; column <= maxColumn; column++) {
                Position position = new Position(new Row(row), new Column(column));
                Piece piece = pieces.get(position);
                if (piece == null) {
                    throw new IllegalArgumentException("비어 있는 좌표가 존재합니다. position=" + position);
                }
                sb.append(cell(piece)).append("|");
            }
            sb.append(System.lineSeparator());
        }
        appendColumnHeader(sb, minColumn, maxColumn);
        return sb.toString();
    }

    private static void appendColumnHeader(StringBuilder sb, int minColumn, int maxColumn) {
        sb.append("   ");
        for (int column = minColumn; column <= maxColumn; column++) {
            sb.append(" ")
                .append(padLeft(String.valueOf(column), 2))
                .append("  ");
        }
        sb.append(System.lineSeparator());
    }

    private static String cell(Piece piece) {
        String symbol = symbolOf(piece);
        if (piece.isEmpty()) {
            return " " + symbol + " ";
        }
        return " " + colorize(piece.asFullPiece(), symbol) + " ";
    }

    private static String colorize(FullPiece piece, String symbol) {
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