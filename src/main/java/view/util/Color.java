package view.util;

import dto.TeamDto;

public class Color {
    private static final String HAN_COLOR_CODE = "\u001B[31m";
    private static final String CHO_COLOR_CODE = "\u001B[34m";
    private static final String EXIT_COLOR_CODE = "\u001B[0m";

    public static String apply(TeamDto teamDto, String message) {
        StringBuilder stringBuilder = new StringBuilder();

        if (teamDto == TeamDto.CHO) {
            stringBuilder.append(CHO_COLOR_CODE);
        }

        if (teamDto == TeamDto.HAN) {
            stringBuilder.append(HAN_COLOR_CODE);
        }

        return stringBuilder.append(message).append(EXIT_COLOR_CODE).toString();
    }
}
