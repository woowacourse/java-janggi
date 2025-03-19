package domain.piece;

import domain.Position;
import domain.TeamType;
import java.util.List;

public class King extends Piece {

    public King(Position position, TeamType teamType) {
        super(position, teamType);
    }

    @Override
    public boolean canMove(Position expectedPosition, List<Piece> pieces) {
        return false;
    }
}
