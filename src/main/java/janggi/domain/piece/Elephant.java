package janggi.domain.piece;

import janggi.domain.moveRule.ElephantMoveRule;
import janggi.domain.moveRule.MoveRule;

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

    @Override
    public int score() {
        return 3;
    }
}
