package parser;

public class NumberParser {

    private NumberParser() {
    }

    public static long parse(String input) {
        try {
            return Long.parseLong(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자를 입력해야합니다.");
        }
    }
}
