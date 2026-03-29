package janggi.domain.piece;

import janggi.domain.mouveRule.MoveRule;
import janggi.domain.mouveRule.OneStepMoveRule;

public class Advisor extends Piece {

    public Advisor(Team team) {
        super(team);
    }

    @Override
    public String toString() {
        return "사";
    }

    @Override
    public MoveRule moveRule() {
        return new OneStepMoveRule();
    }

}
