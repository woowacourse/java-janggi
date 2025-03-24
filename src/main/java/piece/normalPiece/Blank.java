package piece.normalPiece;

import piece.PieceType;
import piece.Team;
import position.Position;
import route.Routes;

public class Blank extends NormalPiece{

    public Blank(Position position) {
        super(Team.BLANK, position, Routes.ofBlank());
    }

    @Override
    public PieceType type() {
        return PieceType.BLANK;
    }
}
