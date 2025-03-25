package view;

public class Painter {

    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String WHITE = "\u001B[37m";
    private static final String RESET = "\u001B[0m";

    public static String paintRed(String text) {
        return RED + text + RESET;
    }

    public static String paintGreen(String text) {
        return GREEN + text + RESET;
    }

    public static String paintWhite(String text) {
        return WHITE + text + RESET;
    }
}
