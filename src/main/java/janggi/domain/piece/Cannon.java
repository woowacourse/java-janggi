package janggi.domain.piece;

import janggi.domain.moveRule.CannonMoveRule;
import janggi.domain.moveRule.MoveRule;

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

    @Override
    public int score() {
        return 7;
    }
}
