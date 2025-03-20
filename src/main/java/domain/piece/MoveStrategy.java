package domain.piece;

import domain.BoardLocation;
import java.util.List;

public interface MoveStrategy {

    boolean isMovable(BoardLocation current, BoardLocation destination);

    List<BoardLocation> createAllPath(BoardLocation current, BoardLocation destination);
}
