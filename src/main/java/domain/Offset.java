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

    public boolean isSingleStep() {
        return (Math.abs(dx) == 1 && Math.abs(dy) == 0) || (Math.abs(dx) == 0 && Math.abs(dy) == 1);
    }

    public boolean isStraightMoving() {
        return (Math.abs(dx) != 0 && Math.abs(dy) == 0) || (Math.abs(dx) == 0 && Math.abs(dy) != 0);
    }

    public boolean isHorseMove() {
        return (Math.abs(dx) == 2 && Math.abs(dy) == 1) || (Math.abs(dx) == 1 && Math.abs(dy) == 2);
    }

    public boolean isElephantMove() {
        return (Math.abs(dx) == 3 && Math.abs(dy) == 2) || (Math.abs(dx) == 2 && Math.abs(dy) == 3);
    }

    public int calculateStraightDistance() {
        if (!isStraightMoving()) {
            throw new IllegalStateException("직선 이동이 아닐 때는 직선 거리를 계산할 수 없습니다.");
        }
        return Math.max(Math.abs(dx), Math.abs(dy));
    }

    public Direction getMainDirection() {
        if (Math.abs(dx) > Math.abs(dy)) {
            return Direction.decideXDirection(dx);
        }
        return Direction.decideYDirection(dy);
    }

    public Direction getSubDirection() {
        if (Math.abs(dx) > Math.abs(dy)) {
            return Direction.decideYDirection(dy);
        }
        return Direction.decideXDirection(dx);
    }
}
