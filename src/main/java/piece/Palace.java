package piece;

import position.Position;
import route.Routes;

public class Palace extends Piece {

    public Palace(Team team, Position position) {
        super(team, position, Routes.ofPalace());
    }

    @Override
    public PieceType type() {
        return PieceType.PALACE;
    }
}
