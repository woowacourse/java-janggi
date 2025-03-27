package view;

import java.util.Map;
import piece.PieceType;

public class OutputFormatter {
    private static final Map<PieceType, String> GREEN_PIECE_PRINT_FORMAT = Map.of(
            PieceType.CHARIOT, "c",
            PieceType.CANNON, "n",
            PieceType.GENERAL, "g",
            PieceType.GUARD, "r",
            PieceType.ELEPHANT, "e",
            PieceType.HORSE, "h",
            PieceType.SOLIDER, "s"
    );

    private static final Map<PieceType, String> RED_PIECE_PRINT_FORMAT = Map.of(
            PieceType.CHARIOT, "C",
            PieceType.CANNON, "N",
            PieceType.GENERAL, "G",
            PieceType.GUARD, "R",
            PieceType.ELEPHANT, "E",
            PieceType.HORSE, "H",
            PieceType.SOLIDER, "S"
    );

    public static String getGreenPiecePrintFormatBy(PieceType pieceType) {
        return GREEN_PIECE_PRINT_FORMAT.get(pieceType);
    }

    public static String getRedPiecePrintFormatBy(PieceType pieceType) {
        return RED_PIECE_PRINT_FORMAT.get(pieceType);
    }
}
