package parser;

import domain.position.Position;
import java.util.Arrays;
import java.util.List;

public class PositionParser {

    private static final String SEPARATOR = ",";
    private static final int COORDINATE_SIZE = 2;
    private static final int ROW_INDEX = 0;
    private static final int COLUMN_INDEX = 1;

    private PositionParser() {
    }

    public static Position parsePosition(String input) {
        List<Integer> points = Arrays.stream(input.split(SEPARATOR))
                .map(String::trim)
                .map(PositionParser::parseInt)
                .toList();

        validateSize(points);

        return new Position(points.get(ROW_INDEX), points.get(COLUMN_INDEX));
    }

    private static int parseInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("[ERROR] 숫자로 입력해야 합니다: " + input);
        }
    }

    private static void validateSize(List<Integer> points) {
        if (points.size() != COORDINATE_SIZE) {
            throw new IllegalArgumentException(
                    "[ERROR] 2의 숫자를 ','로 구분하여 입력해야 합니다."
            );
        }
    }

}
