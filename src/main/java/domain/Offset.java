package domain;

import domain.board.Position;

public record Offset(int dx, int dy) {
    public static Offset of(Position source, Position target) {
        return new Offset(
                target.x() - source.x(),
                target.y() - source.y()
        );
    }

    public Offset add(Offset offset) {
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
        return (absX() == absY()) && (absX() != 0);
    }

    public Offset normalize() {
        if(!(this.isStraightMoving() || this.isDiagonalMoving())) {
            throw new IllegalStateException("단위 벡터를 계산할 수 없습니다.");
        }
        return new Offset(Integer.signum(dx), Integer.signum(dy));
    }

    public int absX() {
        return Math.abs(dx);
    }

    public int absY() {
        return Math.abs(dy);
    }
}
