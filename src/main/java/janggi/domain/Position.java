package janggi.domain;

import java.util.List;

public record Position(
    int x,
    int y
) {

    public static Position from(List<String> positions) {
        validatePositions(positions);
        return new Position(Integer.parseInt(positions.get(0)), Integer.parseInt(positions.get(1)));
    }

    private static void validatePositions(List<String> positions) {
        if (positions.size() != 2) {
            throw new IllegalArgumentException("[ERROR] 좌표는 x,y 형식으로 입력 가능합니다.");
        }

        String xInput = positions.get(0);
        String yInput = positions.get(1);

        if (!isValidCoordinate(xInput) || !isValidCoordinate(yInput)) {
            throw new IllegalArgumentException("[ERROR] 좌표의 각 숫자는 양의 정수이어야 합니다.");
        }
    }

    private static boolean isValidCoordinate(String input) {
        return input.matches("0|[1-9][0-9]*");
    }

    public int deltaX(Position other) {
        return other.x - x;
    }

    public int deltaY(Position other) {
        return other.y - y;
    }
}
