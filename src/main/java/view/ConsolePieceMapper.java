package view;

import domain.piece.PieceType;
import domain.player.Team;
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
        SYMBOLS.put(PieceType.NONE, "  ");
    }

    public static String toViewString(Team team, PieceType pieceType) {
        String symbol = SYMBOLS.getOrDefault(pieceType, "  ");

        if (pieceType == PieceType.NONE || team == null) {
            return String.format("[%2s]", symbol);
        }

        if (team.isCho()) {
            return String.format("[%s%2s%s]", ANSI_BLUE, symbol, ANSI_RESET);
        }

        if (team.isHan()) {
            return String.format("[%s%2s%s]", ANSI_RED, symbol, ANSI_RESET);
        }

        return String.format("[%2s]", symbol);
    }
}