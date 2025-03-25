package janggi.turn;

import janggi.piece.Team;

public class HanTurn extends Turn {

    protected HanTurn() {
        super(Team.HAN);
    }

    @Override
    public Turn nextTurn() {
        return new ChoTurn();
    }
}
