package domain.piece;

import domain.board.Direction;
import java.util.List;

public interface StraightMovable extends Piece {

    List<Direction> movableDirections();

    int step();
}
