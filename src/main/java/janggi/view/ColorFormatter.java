package janggi.view;

import janggi.domain.camp.Camp;
import janggi.domain.piece.type.PieceType;

public class ColorFormatter {

    private static final String RED_COLOR_CODE = "\u001B[31m";
    private static final String GREEN_COLOR_CODE = "\u001B[32m";
    private static final String EXIT_CODE = "\u001B[0m";

    public static String getColoredPieceAttributes(Camp camp, PieceType pieceType) {
        String name = pieceType.getName(camp);
        if (camp == Camp.CHU) {
            return colorChu(name);
        }
        return colorHan(name);
    }

    public static String getColoredCampName(Camp camp) {
        if (camp == Camp.CHU) {
            return colorChu("초");
        }
        return colorHan("한");
    }

    private static String colorChu(String value) {
        return GREEN_COLOR_CODE + value + EXIT_CODE;
    }

    private static String colorHan(String value) {
        return RED_COLOR_CODE + value + EXIT_CODE;
    }
}
