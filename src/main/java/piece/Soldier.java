package piece;

import position.Position;
import route.Routes;

public class Soldier extends Piece{

    public Soldier(Position position) {
        super(position, Routes.ofSoldier());
    }

    @Override
    public PieceType type() {
        return PieceType.SOLDIER;
    }
}
