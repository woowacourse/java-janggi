package janggi.util;

public class ColorConvertor {

    private static final String RED_COLOR_CODE = "\u001B[31m";
    private static final String GREEN_COLOR_CODE = "\u001B[32m";
    private static final String EXIT_CODE = "\u001B[0m";

    public static String convertToRed(String value) {
        return RED_COLOR_CODE + value + EXIT_CODE;
    }

    public static String convertToGreen(String value) {
        return GREEN_COLOR_CODE + value + EXIT_CODE;
    }
}
