package janggi.domain.movement;

import janggi.domain.Position;
import janggi.domain.board.BoardMediator;
import java.util.List;

public interface Rule {
    List<Position> execute(Position from, BoardMediator boardMediator);
}
