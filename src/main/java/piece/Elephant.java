package piece;

import position.Position;
import route.Routes;

public class Elephant extends Piece{

    protected Elephant(Position position) {
        super(position, Routes.ofElephant());
    }

    @Override
    public PieceType type() {
        return PieceType.ELEPHANT;
    }
}
