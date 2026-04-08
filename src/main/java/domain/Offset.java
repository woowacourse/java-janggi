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

    public boolean isStraightMoving() {
        return (absX() != 0 && absY() == 0) || (absX() == 0 && absY() != 0);
    }

    public int calculateStraightDistance() {
        if (!isStraightMoving()) {
            throw new IllegalStateException("직선 이동이 아닐 때는 직선 거리를 계산할 수 없습니다.");
        }
        return Math.max(absX(), absY());
    }

    public boolean isDiagonalMoving() {
        return (absX() == absY());
    }

    public Direction getDiagonalDirection() {
        if (!isDiagonalMoving()) {
            throw new IllegalStateException("대각선 이동이 아닐 때는 방향을 계산할 수 없습니다.");
        }
        return Direction.of(new Offset(this.dx() / this.absX(), this.dy() / this.absY()));
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
