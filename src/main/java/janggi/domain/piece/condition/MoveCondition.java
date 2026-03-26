package janggi.domain.piece.condition;

import janggi.domain.Position;
import janggi.domain.board.JanggiBoard;
import janggi.domain.piece.Camp;
import java.util.List;

public interface MoveCondition {

    void checkPath(List<Position> path, Camp camp, JanggiBoard board);
}
