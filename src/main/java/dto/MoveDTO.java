package dto;

import domain.point.Point;

public class MoveDTO {

    private final Point from;
    private final Point to;

    public MoveDTO(InputPointDTO from, InputPointDTO to) {
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
