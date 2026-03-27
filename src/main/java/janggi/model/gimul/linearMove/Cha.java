package janggi.model.gimul.linearMove;

import janggi.model.Team;
import janggi.model.gimul.Gimul;
import java.util.List;

public class Cha extends AbstractLinearMoveGimul {

    public Cha(Team team) {
        super(team);
    }

    @Override
    public boolean canPassThrough(List<Gimul> gimulsOnPath, Gimul gimulAtTo) {
        return gimulsOnPath.isEmpty() && (gimulAtTo == null || !this.isSameTeam(gimulAtTo));
    }

    @Override
    public String getSymbol() {
        return "차";
    }
}
