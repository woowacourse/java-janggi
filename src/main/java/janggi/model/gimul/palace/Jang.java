package janggi.model.gimul.palace;

import janggi.model.Score;
import janggi.model.Team;

public class Jang extends AbstractPalaceGimul {
    private static final int SCORE_VALUE = 0;

    public Jang(Team team) {
        super(team);
    }

    @Override
    public String getSymbol() {
        return "장";
    }

    @Override
    public Score getScore() {
        return new Score(SCORE_VALUE);
    }
}
