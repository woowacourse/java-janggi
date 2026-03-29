package domain;

import domain.board.Position;

public record Offset(int dx, int dy) {
    public static Offset of(Position source, Position target) {
        return new Offset(
                target.getX() - source.getX(),
                target.getY() - source.getY()
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
                source.getX() + dx,
                source.getY() + dy
        );
    }
}
