package dto;

import domain.point.Point;

public class InputPointDto {

    private final Point point;

    public InputPointDto(String input) {
        validate(input);
        this.point = parsePoint(input);
    }

    private void validate(String input) {
        try {
            String[] tokens = input.trim().split("\\s+");
            String y = tokens[0];
            String x = tokens[1];
            Integer.parseInt(y);
            Integer.parseInt(x);
        } catch (RuntimeException e) {
            throw new IllegalArgumentException("잘못된 입력 형식입니다.");
        }
    }

    private Point parsePoint(String input) {
        String[] tokens = input.trim().split("\\s+");
        int integerY = Integer.parseInt(tokens[0]);
        int integerX = Integer.parseInt(tokens[1]);

        return new Point(integerY, integerX);
    }

    public Point getPoint() {
        return this.point;
    }
}
