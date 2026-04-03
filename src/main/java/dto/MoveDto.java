package dto;

import domain.point.Point;

public class MoveDto {

    private final Point from;
    private final Point to;

    public MoveDto(InputPointDto from, InputPointDto to) {
        this.from = from.getPoint();
        this.to = to.getPoint();
    }

    public Point getFrom() {
        return from;
    }

    public Point getTo() {
        return to;
    }

}
