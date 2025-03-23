package janggi.view;

import janggi.piece.Camp;

public final class PieceFormatter {

    private static final String HAN_COLOR_CODE = "\u001B[31m";
    private static final String CHU_COLOR_CODE = "\u001B[32m";
    private static final String EXIT_CODE = "\u001B[0m";

    private PieceFormatter() {
    }

    public static String formatPiece(PieceSymbol pieceSymbol, Camp camp) {
        String displayName = pieceSymbol.getDisplayName();
        return applyColorByCamp(displayName, camp);
    }

    private static String applyColorByCamp(String value, Camp camp) {
        if (camp == Camp.CHU) {
            return CHU_COLOR_CODE + value + EXIT_CODE;
        }
        return HAN_COLOR_CODE + value + EXIT_CODE;
    }
}
