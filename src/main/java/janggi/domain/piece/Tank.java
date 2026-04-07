package janggi.domain.piece;

import janggi.domain.moveRule.MoveRule;
import janggi.domain.moveRule.TankMoveRule;

public class Tank extends Piece {

    public Tank(Team team) {
        super(team);
    }

    @Override
    public String toString() {
        return "차";
    }

    @Override
    public MoveRule moveRule() {
        return new TankMoveRule();
    }

    @Override
    public int score() {
        return 13;
    }
}
