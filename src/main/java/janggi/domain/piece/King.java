package janggi.domain.piece;

import janggi.domain.moveRule.KingMoveRule;
import janggi.domain.moveRule.MoveRule;

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
        return new KingMoveRule();
    }

    @Override
    public int score() {
        return 0;
    }
}
