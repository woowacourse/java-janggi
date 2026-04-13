package console.util;

import java.util.Arrays;
import java.util.List;

public class InputParser {

    private InputParser() {
    }

    public static int parseNumber(String input) {
        try {
            return Integer.parseInt(input.strip());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 입력입니다. 수를 입력해주세요: " + input);
        }
    }

    public static long parseLong(String input) {
        try {
            return Long.parseLong(input.strip());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 입력입니다. 수를 입력해주세요: " + input);
        }
    }

    public static List<String> parseToken(String input, String delimiter) {
        return Arrays.stream(input.strip()
                        .split(delimiter))
                .map(String::strip)
                .toList();
    }
}
