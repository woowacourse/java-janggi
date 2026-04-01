package util;

public class Parser {

    public static int parseInput(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("유효한 입력값이 아닙니다.");
        }
    }
}
