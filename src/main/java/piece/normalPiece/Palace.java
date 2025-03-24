package piece.normalPiece;

import piece.PieceType;
import piece.Team;
import position.Position;
import route.Routes;

public class Palace extends NormalPiece {

    public Palace(Team team, Position position) {
        super(team, position, Routes.ofPalace());
    }

    @Override
    public PieceType type() {
        return PieceType.PALACE;
    }
}
