package janggi.domain.piece;

import janggi.domain.Position;
import java.util.List;

public interface PieceBehavior {

    String toName();

    /**
     * 현재 위치에서 움직일 수 있는 Position을 전달한다.
     **/

    List<Position> generateMovePosition(Side side, Position position);


}
