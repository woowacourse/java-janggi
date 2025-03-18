package piece;

import move.CastleMovement;
import move.Position;

public class General extends Piece {

    public General(int x, int y) {
        super(new CastleMovement(), new Position(x, y));
    }
}
