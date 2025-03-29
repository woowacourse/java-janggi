package domain.board;

import java.util.Objects;

public record BoardVector(
        int dx,
        int dy
) {

    public static BoardVector between(BoardLocation current, BoardLocation destination) {
        return new BoardVector(destination.x() - current.x(), destination.y() - current.y());
    }

    public boolean isAxis() {
        return dx == 0 || dy == 0;
    }

    public boolean isQuadrant() {
        return Math.abs(dx) == Math.abs(dy);
    }

    public boolean isStepAxisMove(int step) {
        return Math.abs(dx) == step || Math.abs(dy) == step;
    }

    public boolean isStepQuadrantMove(int step) {
        return Math.abs(dx) == step && Math.abs(dy) == step;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BoardVector boardVector)) {
            return false;
        }
        return dx == boardVector.dx && dy == boardVector.dy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dx, dy);
    }
}
