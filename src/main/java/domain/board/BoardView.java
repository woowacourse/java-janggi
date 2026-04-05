package domain.board;

import domain.position.Position;

public interface BoardView {
    boolean isEmpty(Position position);

    boolean isCannon(Position position);
}
