package janggi.domain.piece;

import janggi.domain.moveRule.HorseMoveRule;
import janggi.domain.moveRule.MoveRule;

public class Horse extends Piece {

    public Horse(Team team) {
        super(team);
    }

    @Override
    public String toString() {
        return "마";
    }

    @Override
    public MoveRule moveRule() {
        return new HorseMoveRule();
    }

    @Override
    public int score() {
        return 5;
    }

}
