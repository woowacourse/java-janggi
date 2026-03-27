package janggi.model.gimul.linearMove;

import janggi.model.Team;
import janggi.model.gimul.Gimul;
import java.util.List;

public class Pho extends AbstractLinearMoveGimul {
    public Pho(Team team) {
        super(team);
    }

    @Override
    public boolean canPassThrough(List<Gimul> gimulsOnPath, Gimul gimulAtTo) {
        return gimulsOnPath.size() == 1
                && !(gimulsOnPath.getFirst() instanceof Pho)
                && (gimulAtTo == null || !this.isSameTeam(gimulAtTo));
    }

    @Override
    public String getSymbol() {
        return "포";
    }
}
