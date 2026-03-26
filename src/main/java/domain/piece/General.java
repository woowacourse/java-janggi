package domain.piece;

import domain.coordination.Coordination;

import java.util.Map;

public class General extends AbstractPiece {

    public General(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board) {

    }
}
