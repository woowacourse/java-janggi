package domain.piece;

import domain.Position;
import domain.TeamType;
import java.util.List;

public class Cannon extends Piece {

    public Cannon(Position position, TeamType teamType) {
        super(position, teamType);
    }

    @Override
    public boolean canMove(Position expectedPosition, List<Piece> pieces) {
        return false;
    }
}
