package piece;

import position.Position;
import route.Routes;

public class Soldier extends Piece{

    public Soldier(Team team, Position position) {
        super(team, position, Routes.ofSoldier());
    }

    @Override
    public PieceType type() {
        return PieceType.SOLDIER;
    }
}
