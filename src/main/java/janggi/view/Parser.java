package janggi.view;

import janggi.model.Position;
import java.util.List;

public class Parser {

    public static List<Position> parsePositions(String content) {
        String[] positions = content.split(", ");
        String[] startPosition = positions[0].split("");
        String[] endPosition = positions[1].split("");
        if (positions.length != 2) {
            throw new IllegalArgumentException("입력 형식을 확인해주세요.");
        }
        return List.of(
                new Position(parseInt(startPosition[0]), parseInt(startPosition[1])),
                new Position(parseInt(endPosition[0]), parseInt(endPosition[1]))
        );
    }

    private static int parseInt(final String number) {
        try {
            if (number.equals("0")) {
                return 10;
            }
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자를 입력해 주세요.");
        }
    }
}
