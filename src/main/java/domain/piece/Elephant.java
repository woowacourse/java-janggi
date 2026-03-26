package domain.piece;

import domain.coordination.Coordination;

import java.util.Map;

public class Elephant extends AbstractPiece {

    public Elephant(Team team) {
        super(team);
    }

    @Override
    public void validateMovable(Coordination from, Coordination to, Map<Coordination, Piece> board) {

    }
}
