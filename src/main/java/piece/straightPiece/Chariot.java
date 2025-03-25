package piece.straightPiece;

import java.util.Set;
import piece.PieceType;
import piece.Team;
import position.Board;
import position.Position;
import route.Routes;

public class Chariot extends StraightPiece{

    protected Chariot(Team team, Position position) {
        super(team, position, Routes.ofChariot());
    }

    @Override
    public PieceType type() {
        return PieceType.CHARIOT;
    }
}
