package view;

import java.util.Map;
import pieces.Piece;
import pieces.PieceType;
import pieces.Side;

public class DisplayPiece {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final Map<PieceType, String> PIECE_SYMBOLS = Map.of(
        PieceType.CHA, "CH",
        PieceType.MA, "MA",
        PieceType.SANG, "SG",
        PieceType.SA, "SA",
        PieceType.GUNG, "GU",
        PieceType.PO, "PO",
        PieceType.JOL_BYEONG, "JB"
    );

    private DisplayPiece() {
    }

    public static String symbolOf(final Piece piece) {
        final String symbol = PIECE_SYMBOLS.get(piece.type());
        if (symbol == null) {
            throw new IllegalArgumentException("출력할 수 없는 기물 타입입니다. type=" + piece.type());
        }
        return colorize(piece, symbol);
    }

    private static String colorize(final Piece piece, final String symbol) {
        if (piece.isSameSide(Side.HAN)) {
            return ANSI_RED + symbol + ANSI_RESET;
        }
        if (piece.isSameSide(Side.CHO)) {
            return ANSI_GREEN + symbol + ANSI_RESET;
        }
        return symbol;
    }
}
