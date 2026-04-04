package janggi.model.gimul.linearMove;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import java.util.List;
import java.util.Optional;

public class Cha extends AbstractLinearMoveGimul {
    private static final int SCORE_VALUE = 13;

    public Cha(Team team) {
        super(team);
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath, Optional<AbstractGimul> gimulAtTo) {
        return gimulsOnPath.isEmpty()
                && gimulAtTo.map(gimul -> !this.isSameTeam(gimul)).orElse(true);
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
