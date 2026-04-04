package util;

public class Parser {

    private static final String PARSE_INPUT_MESSAGE = "유효한 입력값이 아닙니다.";

    public static int parseInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(PARSE_INPUT_MESSAGE);
        }
    }
}
