package janggi.model.gimul.linearMove;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import java.util.List;

public class Cha extends AbstractLinearMoveGimul {
    private static final int SCORE_VALUE = 13;

    public Cha(Team team) {
        super(team);
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath) {
        return gimulsOnPath.isEmpty();
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath, AbstractGimul abstractGimulAtTo) {
        return gimulsOnPath.isEmpty() && !this.isSameTeam(abstractGimulAtTo);
    }

    @Override
    public String getSymbol() {
        return "차";
    }

    @Override
    public Score getScore() {
        return new Score(SCORE_VALUE);
    }
}
