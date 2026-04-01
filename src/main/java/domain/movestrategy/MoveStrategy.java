package domain.movestrategy;

import domain.board.Position;
import domain.piece.Piece;
import java.util.List;
import java.util.Map;

public interface MoveStrategy {

    List<Position> calculateMovablePositions(Position from, Map<Position, Piece> pieces);
}
