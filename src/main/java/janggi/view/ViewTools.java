package janggi.view;

public class ViewTools {

    public static final String RED = "\u001B[31m";
    public static final String BLUE = "\u001B[34m";
    public static final String RESET = "\u001B[0m";

    public static final String FULL_WIDTH_BAR = "＿";
    public static final String FULL_WIDTH_SPACE = "　";

    public static String applyColor(String color, String text) {
        return color + text + RESET;
    }
}
