package domain.board;

import domain.position.Position;

public interface BoardView {

    boolean isEmpty(Position position);

    boolean isSameSide(Position from, Position to);

    boolean isCannon(Position position);
}

