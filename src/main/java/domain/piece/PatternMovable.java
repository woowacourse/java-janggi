package domain.piece;

import domain.board.Movement;
import java.util.List;

public interface PatternMovable extends Piece {

    List<Movement> movements();
}
