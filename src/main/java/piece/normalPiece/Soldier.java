package piece.normalPiece;

import piece.PieceType;
import piece.Team;
import position.Position;
import route.Routes;

public class Soldier extends NormalPiece{

    public Soldier(Team team, Position position) {
        super(team, position, Routes.ofSoldier());
    }

    @Override
    public PieceType type() {
        return PieceType.SOLDIER;
    }
}
