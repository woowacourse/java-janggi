package janggi.view;

import janggi.domain.Team;

public final class Formatter {

    private Formatter() {
    }

    public static String formatSide(Team team) {
        String sideName = team.toColorString("초나라");
        if (team.isSameSide(Team.HAN)) {
            sideName = team.toColorString("한나라");
        }
        return sideName;
    }

    public static String formatMessageWithHeader(String header, String message) {
        return header + message;
    }

    public static String formatFullWidthNumber(int number) {
        String string = String.valueOf(number);

        StringBuilder builder = new StringBuilder();
        for (char value : string.toCharArray()) {
            builder.append((char) (value - '0' + '０'));
        }

        return builder.toString();
    }

    public static String formatScoreBySide(Team team, double score) {
        return Formatter.formatSide(team) + " : " + score + "점";
    }
}
