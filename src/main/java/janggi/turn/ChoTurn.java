package janggi.turn;

import janggi.piece.Team;

public class ChoTurn extends Turn {

    public ChoTurn() {
        super(Team.CHO);
    }

    @Override
    public Turn nextTurn() {
        return new HanTurn();
    }
}
