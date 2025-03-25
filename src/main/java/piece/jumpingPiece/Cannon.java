package piece.jumpingPiece;

import piece.PieceType;
import piece.Team;
import position.Position;
import route.Routes;

public class Cannon extends JumpingPiece {

    protected Cannon(Team team, Position position) {
        super(team, position, Routes.ofCannon());
    }

    @Override
    public PieceType type() {
        return PieceType.CANNON;
    }
}
