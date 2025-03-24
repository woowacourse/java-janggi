package piece;

import position.Position;
import route.Routes;

public class ChoPawn extends Piece{

    protected ChoPawn(Position position) {
        super(Team.CHO, position, Routes.ofChoPawn());
    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }
}
