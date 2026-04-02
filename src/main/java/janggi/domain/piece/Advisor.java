package janggi.domain.piece;

import janggi.domain.mouveRule.KingMoveRule;
import janggi.domain.mouveRule.MoveRule;

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
        return new KingMoveRule();
    }
}
