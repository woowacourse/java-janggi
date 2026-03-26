package util;

public class Parser {

    public static int parseToPlacementCode(String input) {
        if (!input.matches("^[1|2|3|4]$")) {
            throw new IllegalArgumentException("코드는 1, 2, 3, 4만 입력 가능합니다.");
        }
        return Integer.parseInt(input);
    }

    public static int parseToSideCode(String input) {
        if (!input.matches("^[1|2]$")) {
            throw new IllegalArgumentException("코드는 1, 2만 입력 가능합니다.");
        }
        return Integer.parseInt(input);
    }
}
