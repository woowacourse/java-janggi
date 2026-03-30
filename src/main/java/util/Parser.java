package util;

import domain.position.Position;

public class Parser {

    private Parser() {}

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

    public static Position parseToPosition(String input) {
        if (!input.matches("^(10|[1-9]),[1-9]$")) {
            throw new IllegalArgumentException("좌표를 row는 1-10, column은 1-9 까지만 가능합니다.");
        }
        String[] inputPosition = input.split(",");
        return Position.of(Integer.parseInt(inputPosition[0]), Integer.parseInt(inputPosition[1]));
    }
}
