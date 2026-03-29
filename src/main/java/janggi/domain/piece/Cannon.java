package janggi.domain.piece;

import janggi.domain.mouveRule.CannonMoveRule;
import janggi.domain.mouveRule.MoveRule;

public class Cannon extends Piece {

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public String toString() {
        return "포";
    }

    @Override
    public MoveRule moveRule() {
        return new CannonMoveRule();
    }
}
