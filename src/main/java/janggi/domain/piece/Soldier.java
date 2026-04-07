package janggi.domain.piece;

import janggi.domain.moveRule.MoveRule;
import janggi.domain.moveRule.SoldierMoveRule;

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
