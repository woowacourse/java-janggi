package dto;

import domain.point.Point;

public class Move {

    private final Point from;
    private final Point to;

    public Move(Point from, Point to) {
        this.from = from;
        this.to = to;
    }

    public Point getFrom() {
        return from;
    }

    public Point getTo() {
        return to;
    }

}
