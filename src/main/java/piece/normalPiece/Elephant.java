package piece.normalPiece;

import piece.PieceType;
import piece.Team;
import position.Position;
import route.Routes;

public class Elephant extends NormalPiece{

    public Elephant(Team team, Position position) {
        super(team, position, Routes.ofElephant());
    }

    @Override
    public PieceType type() {
        return PieceType.ELEPHANT;
    }
}
