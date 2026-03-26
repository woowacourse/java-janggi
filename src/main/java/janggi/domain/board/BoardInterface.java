package janggi.domain.board;

import janggi.domain.Position;
import janggi.domain.Side;

public interface BoardInterface {
    boolean isEmpty(Position position);
    boolean isPo(Position position);
    boolean isEnemy(Side side, Position position);
    boolean isAlly(Side side, Position position);
}
