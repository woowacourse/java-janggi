package janggi.model.gimul.palace;

import janggi.model.Score;
import janggi.model.Team;

public class Sa extends AbstractPalaceGimul {
    private static final int SCORE_VALUE = 3;

    public Sa(Team team) {
        super(team);
    }

    @Override
    public String getSymbol() {
        return "사";
    }

    @Override
    public Score getScore() {
        return new Score(SCORE_VALUE);
    }
}
