package domain.game;

import domain.point.Point;

public class MoveCommand {
    private final Point from;
    private final Point to;

    public MoveCommand(Point from, Point to) {
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
