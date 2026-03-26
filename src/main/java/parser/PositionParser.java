package parser;

import domain.position.Position;
import java.util.Arrays;

public class PositionParser {

    private static final String SEPARATOR = ",";
    private static final int COORDINATE_SIZE = 2;

    private PositionParser() {}

    public static Position parsePosition(String input) {

        int[] coordinates = Arrays.stream(input.split(SEPARATOR))
                .map(String::trim) // 앞뒤 공백 제거
                .mapToInt(PositionParser::parseInt) // int 기본형으로 바로 변환
                .toArray();

        validateSize(coordinates);

        return new Position(coordinates[0], coordinates[1]);
    }

    private static int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자로 입력해야 합니다: " + input);
        }
    }

    private static void validateSize(int[] coordinates) {
        if (coordinates.length != COORDINATE_SIZE) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d개의 숫자를 '%s'로 구분하여 입력해야 합니다.", COORDINATE_SIZE, SEPARATOR)
            );
        }
    }
}
