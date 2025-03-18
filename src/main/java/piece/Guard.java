package piece;

import move.MoveStrategy;
import move.Position;

public class Guard extends Piece {

    public Guard(MoveStrategy moveStrategy, int x, int y) {
        super(moveStrategy, new Position(x, y));
    }
}
