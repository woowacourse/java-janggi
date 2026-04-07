package janggi.domain.piece;

import janggi.domain.moveRule.KingMoveRule;
import janggi.domain.moveRule.MoveRule;

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

    @Override
    public int score() {
        return 3;
    }

}
