package janggi.domain.piece;

import janggi.domain.Board;
import janggi.domain.Position;
import java.util.Set;

public interface PieceBehavior {

    String toName();

    /**
     * 현재 위치에서 움직일 수 있는 Position을 전달한다.
     **/

    Set<Position> generateMovePosition(Side side, Position position);

    Set<Position> generateMovePosition(Board board, Side side, Position position);
}
