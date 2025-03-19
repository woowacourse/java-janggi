package domain;

public abstract class Piece {

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.getClass() == obj.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    abstract boolean isMovable(BoardLocation current, BoardLocation target);
}
