package janggi.domain.piece.movement.condition;

import janggi.domain.board.BoardChecker;
import janggi.domain.board.Position;
import janggi.domain.piece.Camp;
import java.util.List;

public interface MoveCondition {

    void checkPath(List<Position> path, Camp camp, BoardChecker board);
}
