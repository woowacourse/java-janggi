package team.janggi.util;

import java.util.Arrays;
import java.util.List;

public class Parser {

    public static int parseByInteger(String input, String errorMessage) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
    
    public static List<Integer> parseByDelimiter(String input, String errorMessage) {
        String[] split = input.split(" ");
        if (split.length != 2) {
            throw new IllegalArgumentException("[ERROR] 두 개의 숫자를 입력해주세요.");
        }
        return Arrays.stream(split)
                .map(str -> parseByInteger(str, errorMessage))
                .toList();
    }
}
