package domain;

import domain.board.Position;

public record Offset(int dx, int dy) {
    public static Offset of(Position source, Position target) {
        return new Offset(
                target.x() - source.x(),
                target.y() - source.y()
        );
    }

    public Offset move(Direction direction) {
        Offset offset = direction.getOffset();
        return new Offset(
                this.dx + offset.dx,
                this.dy + offset.dy
        );
    }

    public Position applyTo(Position source) {
        return new Position(
                source.x() + dx,
                source.y() + dy
        );
    }

    public int absX() {
        return Math.abs(dx);
    }

    public int absY() {
        return Math.abs(dy);
    }

    public Direction getMainDirection() {
        if (absX() > absY()) {
            return Direction.decideXDirection(dx);
        }
        return Direction.decideYDirection(dy);
    }

    public Direction getSubDirection() {
        if (absX() > absY()) {
            return Direction.decideYDirection(dy);
        }
        return Direction.decideXDirection(dx);
    }
}
