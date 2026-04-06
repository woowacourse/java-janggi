package janggi.view.util;

public final class Parser {

    public static int parseInt(String target) {
        try {
            return Integer.parseInt(target);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("정수가 아닙니다.");
        }
    }

    public static long parseLong(String target) {
        try {
            return Long.parseLong(target);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("정수가 아닙니다.");
        }
    }
}
