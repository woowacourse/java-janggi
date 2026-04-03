package janggi.domain.piece.strategy;

import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import java.util.List;

public interface MoveStrategy {

    List<Position> findPath(Position source, Position destination, Camp camp);
}
