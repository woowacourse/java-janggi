package janggi.utils;

public class StringParser {

    public static int parseInt(final String input) {
        try {
            return Integer.parseInt(input);
        } catch (final NumberFormatException exception) {
            throw new IllegalArgumentException("[ERROR] 숫자가 아닙니다.");
        }
    }
}
