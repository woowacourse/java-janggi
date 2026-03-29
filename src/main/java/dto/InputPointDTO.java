package dto;

import domain.point.Point;

public class moveInputDTO {

    private final Point point;

    public moveInputDTO(String y, String x) {
        validate(y, x);
        this.point = parsePoint(y, x);
    }

    private void validate(String y, String x){
        int integerY;
        int integerX;
        try {
            integerY = Integer.parseInt(y);
            integerX = Integer.parseInt(x);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 입력 형식입니다.");
        }
    }

    private Point parsePoint(String y, String x){
        int integerY = Integer.parseInt(y);
        int integerX = Integer.parseInt(x);

        return new Point(integerY, integerX);
    }
}
