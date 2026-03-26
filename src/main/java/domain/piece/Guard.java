package domain.piece;

import domain.coordination.Coordination;

import java.util.Map;

public class Guard extends AbstractPiece {

    public Guard(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board) {

    }
}
