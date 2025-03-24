package piece;

import position.Position;
import route.Routes;

public abstract class Piece {
    private final Position position;

    public Piece(Position position) {
        this.position = position;
    }

    public boolean canMove(Position destination) {
        return false;
    }

    public abstract Routes routes();
}
