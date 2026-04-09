package janggi.model.gimul.palace;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.gimul.GimulType;

public class Jang extends AbstractPalaceGimul {
    private static final int SCORE_VALUE = 0;

    public Jang(Team team) {
        super(team, GimulType.JANG);
    }

    @Override
    public String getSymbol() {
        return "장";
    }

    @Override
    public Score getScore() {
        return new Score(SCORE_VALUE);
    }

    @Override
    public boolean canBeJumpedOver() {
        return true;
    }

    @Override
    public boolean isKing() {
        return true;
    }
}
