package janggi.domain.piece;

import janggi.domain.mouveRule.MoveRule;
import janggi.domain.mouveRule.SoldierMoveRule;

public class Soldier extends Piece {

    public Soldier(Team team) {
        super(team);
    }

    @Override
    public String toString() {
        return "졸";
    }

    @Override
    public MoveRule moveRule() {
        return new SoldierMoveRule(findTeam());
    }

    @Override
    public int score() {
        return 2;
    }
}
