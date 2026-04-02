package view;

import domain.piece.BasicPiece;
import domain.piece.PieceType;
import java.util.EnumMap;
import java.util.Map;

public class ConsolePieceMapper {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private static final Map<PieceType, String> SYMBOLS = new EnumMap<>(PieceType.class);

    static {
        SYMBOLS.put(PieceType.CHA, "CH");
        SYMBOLS.put(PieceType.MA, "MA");
        SYMBOLS.put(PieceType.SA, "SA");
        SYMBOLS.put(PieceType.SANG, "SD");
        SYMBOLS.put(PieceType.JANG, "JA");
        SYMBOLS.put(PieceType.PO, "PO");
        SYMBOLS.put(PieceType.JOL, "ZO");
    }

    public static String toViewString(BasicPiece piece) {
        String symbol = SYMBOLS.getOrDefault(piece.getPieceType(), "  ");

        if (piece.getTeam().isCho()) {
            return String.format("[%s%2s%s]", ANSI_BLUE, symbol, ANSI_RESET);
        }

        if (piece.getTeam().isHan()) {
            return String.format("[%s%2s%s]", ANSI_RED, symbol, ANSI_RESET);
        }

        return String.format("[%2s]", symbol);
    }

    public static String toEmptyString() {
        return "[  ]";
    }
}
