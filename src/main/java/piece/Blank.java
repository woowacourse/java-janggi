package piece;

import position.Position;
import route.Routes;

public class Blank extends Piece{

    public Blank(Position position) {
        super(Team.BLANK, position, Routes.ofBlank());
    }

    @Override
    public PieceType type() {
        return PieceType.BLANK;
    }
}
