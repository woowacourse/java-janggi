package janggi.view;

public final class ConsoleColor {

    private static final String RED = "\u001B[31m";
    private static final String BLUE = "\u001B[34m";
    private static final String GREEN_BACKGROUND = "\u001B[42m";
    private static final String EXIT = "\u001B[0m";

    private ConsoleColor() {
    }

    public static String red(final String string) {
        return RED + string + EXIT;
    }

    public static String blue(final String string) {
        return BLUE + string + EXIT;
    }

    public static String getGreenBackground(final String string) {
        return GREEN_BACKGROUND + string + EXIT;
    }
}
