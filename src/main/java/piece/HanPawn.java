package piece;

import position.Position;
import route.Routes;

public class HanPawn extends Piece {

    public HanPawn(Position position) {
        super(Team.HAN, position, Routes.ofHanPawn());
    }

    @Override
    public PieceType type() {
        return PieceType.PAWN;
    }
}
