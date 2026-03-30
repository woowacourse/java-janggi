package janggi.model.gimul.palace;

import janggi.model.Team;

public class Jang extends AbstractPalaceGimul {
    public Jang(Team team) {
        super(team);
    }

    @Override
    public String getSymbol() {
        return "장";
    }
}
