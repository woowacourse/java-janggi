package domain;

public interface MoveStrategy {

    boolean isMovable(BoardLocation current, BoardLocation destination);
}
