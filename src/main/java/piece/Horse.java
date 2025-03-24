package piece;

import position.Position;
import route.Routes;

public class Horse extends Piece{

    protected Horse(Position position) {
        super(position, Routes.ofHorse());
    }

    @Override
    public PieceType type() {
        return PieceType.HORSE;
    }
}
