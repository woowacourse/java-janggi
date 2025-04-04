package domain.board;

import java.util.Objects;

public record BoardVector(
        int dx,
        int dy
) {

    public static BoardVector between(BoardLocation current, BoardLocation destination) {
        return new BoardVector(destination.column() - current.column(), destination.row() - current.row());
    }

    public boolean isNotAxis() {
        return dx != 0 && dy != 0;
    }

    public boolean isNotDiagonal() {
        return Math.abs(dx) != Math.abs(dy);
    }

    public boolean hasStepAxisMove(int step) {
        return Math.abs(dx) == step || Math.abs(dy) == step;
    }

    public boolean hasStepDiagonalMove(int step) {
        return Math.abs(dx) == step && Math.abs(dy) == step;
    }

    public int getAbsDx() {
        return Math.abs(dx);
    }

    public int getAbsDy() {
        return Math.abs(dy);
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
