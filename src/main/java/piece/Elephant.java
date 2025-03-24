package piece;

import position.Position;
import route.Routes;

public class Elephant extends Piece{

    public Elephant(Team team, Position position) {
        super(team, position, Routes.ofElephant());
    }

    @Override
    public PieceType type() {
        return PieceType.ELEPHANT;
    }
}
