package janggi.view;

import janggi.domain.Side;

public final class Formatter {

    private Formatter() {
    }

    public static String formatSide(Side side) {
        String sideName = "\u001B[32m초나라\u001B[0m";
        if (side == Side.HAN) {
            sideName = "\u001B[31m한나라\u001B[0m";
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
}
