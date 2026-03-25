package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.piece.Camp;
import java.util.List;

public interface MoveStrategy {

    List<Position> findPath(Position from, Position to, Camp camp);
}
