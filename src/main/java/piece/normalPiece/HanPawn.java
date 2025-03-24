package piece.normalPiece;

import piece.PieceType;
import piece.Team;
import position.Position;
import route.Routes;

public class HanPawn extends NormalPiece {

    public HanPawn(Position position) {
        super(Team.HAN, position, Routes.ofHanPawn());
    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }
}
