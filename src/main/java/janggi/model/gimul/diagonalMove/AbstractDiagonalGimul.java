package janggi.model.gimul.diagonalMove;

import janggi.model.Team;
import janggi.model.gimul.AbstractGimul;
import java.util.List;
import java.util.Optional;

public abstract class AbstractDiagonalGimul extends AbstractGimul {

    public AbstractDiagonalGimul(Team team) {
        super(team);
    }

    @Override
    public boolean canPassThrough(List<AbstractGimul> gimulsOnPath, Optional<AbstractGimul> gimulAtTo) {
        return gimulsOnPath.isEmpty()
                && gimulAtTo.map(gimul -> !this.isSameTeam(gimul)).orElse(true);
    }
}
