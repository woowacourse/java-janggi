package view;

public class Painter {

    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String WHITE = "\u001B[37m";
    public static final String RESET = "\u001B[0m";

    public static String paintRed(String text) {
        return RED + text;
    }

    public static String paintGreen(String text) {
        return GREEN + text;
    }

    public static String paintWhite(String text) {
        return WHITE + text;
    }

    public static void clean() {
        System.out.print(RESET);
    }
}
