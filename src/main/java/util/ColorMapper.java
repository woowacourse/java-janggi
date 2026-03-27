package util;

import java.util.Map;

public class ColorMapper {
    private static final String RESET = "\u001B[0m";
    private static final Map<String, String> COLOR_CODES = Map.of(
            "C", "\u001B[34m",
            "H", "\u001B[31m"
    );

    private ColorMapper() {
    }

    public static String colorize(String text, String colorName) {
        String code = COLOR_CODES.getOrDefault(colorName, "");
        return code + text + RESET;
    }
}
