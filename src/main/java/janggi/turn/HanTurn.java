package janggi.turn;

import janggi.piece.Team;

public class HanTurn extends Turn {

    private static final String HAN_NAME = "한";

    @Override
    public Turn nextTurn() {
        return new ChoTurn();
    }

    @Override
    public String getTurnName() {
        return HAN_NAME;
    }

    @Override
    public boolean isMovingSameTeam(final Team team) {
        return team == Team.HAN;
    }
}
