package domain.piece;

import domain.BoardLocation;

public interface MoveStrategy {

    boolean isMovable(BoardLocation current, BoardLocation destination);
}
