package janggi.model.gimul.diagonalMove;

import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import java.util.List;

public abstract class AbstractDiagonalGimul extends AbstractGimul {

    protected AbstractDiagonalGimul(Team team) {
        super(team);
    }

    @Override
    public boolean canPassThrough(
            List<AbstractGimul> gimulsOnPath,
            AbstractGimul abstractGimulAtTo
    ) {
        return gimulsOnPath.isEmpty() && !this.isSameTeam(abstractGimulAtTo);
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath) {
        return gimulsOnPath.isEmpty();
    }
}
