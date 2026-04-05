package domain.movestrategy;

import domain.board.Board;
import domain.board.Position;
import java.util.List;

public interface MoveStrategy {

    List<Position> getMovablePositions(final Board board, final Position from);
}
