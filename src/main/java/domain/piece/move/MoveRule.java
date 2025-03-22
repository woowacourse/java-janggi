package domain.piece.move;

import domain.position.Position;
import java.util.List;

public interface MoveRule {
    List<Position> getIntermediatePath(Position from, Position to);
}
