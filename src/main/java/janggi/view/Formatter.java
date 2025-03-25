package janggi.view;

import janggi.domain.Side;

public final class Formatter {

    private Formatter() {
    }

    public static String formatSide(Side side) {
        String sideName = side.toColorString("초나라");
        if (side.isSameSide(Side.HAN)) {
            sideName = side.toColorString("한나라");
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

    public static String formatScoreBySide(Side side, double score) {
        return Formatter.formatSide(side) + " : " + score + "점";
    }
}
