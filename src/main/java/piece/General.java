package piece;

import move.MoveStrategy;
import move.Position;

public class General extends Piece {

    public General(MoveStrategy moveStrategy, int x, int y) {
        super(moveStrategy, new Position(x, y));
    }
}
