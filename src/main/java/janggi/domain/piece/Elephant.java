package janggi.domain.piece;

import janggi.domain.mouveRule.ElephantMoveRule;
import janggi.domain.mouveRule.MoveRule;

public class Elephant extends Piece {

    public Elephant(Team team) {
        super(team);
    }

    @Override
    public String toString() {
        return "상";
    }

    @Override
    public MoveRule moveRule() {
        return new ElephantMoveRule();
    }
}
