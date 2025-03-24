package piece.normalPiece;

import piece.PieceType;
import piece.Team;
import position.Position;
import route.Routes;

public class Horse extends NormalPiece{

    protected Horse(Team team, Position position) {
        super(team, position, Routes.ofHorse());
    }

    @Override
    public PieceType type() {
        return PieceType.HORSE;
    }
}
