package piece;

import position.Position;
import route.Routes;

public class Horse extends Piece{

    protected Horse(Team team, Position position) {
        super(team, position, Routes.ofHorse());
    }

    @Override
    public PieceType type() {
        return PieceType.HORSE;
    }
}
