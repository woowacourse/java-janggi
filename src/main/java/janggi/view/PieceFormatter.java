package janggi.view;

import janggi.piece.Camp;
import janggi.piece.PieceSymbol;
import java.util.Map;

public final class PieceFormatter {

    private static final String HAN_COLOR_CODE = "\u001B[31m";
    private static final String CHU_COLOR_CODE = "\u001B[32m";
    private static final String EXIT_CODE = "\u001B[0m";

    private static final Map<PieceSymbol, String> PIECE_SYMBOL_TO_DISPLAY_NAME = Map.of(
            PieceSymbol.CANNON, "포",
            PieceSymbol.CHARIOT, "차",
            PieceSymbol.ELEPHANT, "상",
            PieceSymbol.GENERAL, "왕",
            PieceSymbol.GUARD, "사",
            PieceSymbol.HORSE, "마",
            PieceSymbol.SOLDIER_JOL, "졸",
            PieceSymbol.SOLDIER_BYEONG, "병"
    );

    private PieceFormatter() {
    }

    public static String formatPiece(PieceSymbol pieceSymbol, Camp camp) {
        String displayName = PIECE_SYMBOL_TO_DISPLAY_NAME.get(pieceSymbol);
        return applyColorByCamp(displayName, camp);
    }

    private static String applyColorByCamp(String value, Camp camp) {
        if (camp == Camp.CHU) {
            return CHU_COLOR_CODE + value + EXIT_CODE;
        }
        return HAN_COLOR_CODE + value + EXIT_CODE;
    }
}
