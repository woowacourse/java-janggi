package domain.movestrategy;

import domain.piece.Piece;
import domain.piece.Position;
import java.util.List;
import java.util.Map;

public interface MoveStrategy {

    List<Position> calculateMovablePositions(Position from, Map<Position, Piece> pieces);
}
