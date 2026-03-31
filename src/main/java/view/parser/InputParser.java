package view.parser;

import java.util.Arrays;
import java.util.List;

public class InputParser {

    public int parseNumber(String input) {
        try {
            return Integer.parseInt(input.strip());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("잘못된 입력입니다. 수를 입력해주세요: " + input);
        }
    }

    public List<String> parseToken(String input, String delimiter) {
        return Arrays.stream(input.strip()
                        .split(delimiter))
                .map(String::strip)
                .toList();
    }
}
