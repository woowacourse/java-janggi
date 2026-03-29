package janggi.model.gimul.linearMove;

import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import java.util.List;

public class Cha extends AbstractLinearMoveGimul {

    public Cha(Team team) {
        super(team);
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath, AbstractGimul abstractGimulAtTo) {
        return gimulsOnPath.isEmpty() && !this.isSameTeam(abstractGimulAtTo);
    }
}
