package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import java.util.List;

public interface MoveRule {
    List<Position> execute(Position from, BoardMediator boardMediator);
}
