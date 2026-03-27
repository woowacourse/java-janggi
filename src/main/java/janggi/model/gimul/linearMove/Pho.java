package janggi.model.gimul.linearMove;

import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import java.util.List;

public class Pho extends AbstractLinearMoveAbstractGimul {
    public Pho(Team team) {
        super(team);
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath, AbstractGimul abstractGimulAtTo) {
        return gimulsOnPath.size() == 1
                && !(gimulsOnPath.getFirst() instanceof Pho)
                && !this.isSameTeam(abstractGimulAtTo);
    }

    @Override
    public String getSymbol() {
        return "포";
    }
}
