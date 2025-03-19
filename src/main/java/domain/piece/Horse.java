package domain.piece;

import domain.Position;
import domain.TeamType;
import java.util.List;

public class Horse extends Piece {

    public Horse(Position position, TeamType teamType) {
        super(position, teamType);
    }

    @Override
    public boolean canMove(Position expectedPosition, List<Piece> pieces) {
        return false;
    }
}
