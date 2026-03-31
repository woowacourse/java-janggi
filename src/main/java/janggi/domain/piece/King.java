package janggi.domain.piece;

import janggi.domain.mouveRule.MoveRule;
import janggi.domain.mouveRule.OneStepMoveRule;

public class King extends Piece {

    public King(Team team) {
        super(team);
    }

    @Override
    public String toString() {
        return "장";
    }

    @Override
    public MoveRule moveRule() {
        return new OneStepMoveRule();
    }
}
