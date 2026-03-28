package util;

import domain.place.piece.Side;
import java.util.Map;

public class ColorMapper {
    private static final String RESET = "\u001B[0m";
    private static final Map<Side, String> COLOR_CODES = Map.of(
            Side.CHO, "\u001B[34m",
            Side.HAN, "\u001B[31m"
    );

    private ColorMapper() {
    }

    public static String colorize(String text, Side side) {
        String code = COLOR_CODES.getOrDefault(side, "");
        return code + text + RESET;
    }
}
