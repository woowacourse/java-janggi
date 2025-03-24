package piece.normalPiece;

import piece.PieceType;
import piece.Team;
import position.Position;
import route.Routes;

public class ChoPawn extends NormalPiece{

    public ChoPawn(Position position) {
        super(Team.CHO, position, Routes.ofChoPawn());
    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }
}
