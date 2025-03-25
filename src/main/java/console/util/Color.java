package console.util;

import janggi.piece.Team;

public class Color {
    private static final String HAN_COLOR_CODE = "\u001B[31m";
    private static final String CHO_COLOR_CODE = "\u001B[34m";
    private static final String EXIT_COLOR_CODE = "\u001B[0m";

    public static String apply(Team team, String symbol) {
        StringBuilder stringBuilder = new StringBuilder();

        if (team == Team.CHO) {
            stringBuilder.append(CHO_COLOR_CODE);
        }

        if (team == Team.HAN) {
            stringBuilder.append(HAN_COLOR_CODE);
        }

        return stringBuilder.append(symbol).append(EXIT_COLOR_CODE).toString();
    }
}