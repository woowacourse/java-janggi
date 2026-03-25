package domain.movestrategy;

import domain.piece.Position;
import java.util.List;

public interface MoveStrategy {

    List<Position> calculateMovablePositions(Position from);
}
