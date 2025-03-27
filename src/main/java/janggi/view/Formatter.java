package janggi.view;

public final class Formatter {

    private Formatter() {
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
