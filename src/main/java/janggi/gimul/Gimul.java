package janggi.gimul;

import janggi.position.PositionPath;
import janggi.position.Position;
import janggi.Team;
import java.util.List;

public abstract class Gimul {
    protected final Team team;

    protected Gimul(Team team) {
        this.team = team;
    }

    public abstract PositionPath getLegalPath(Position from, Position to);
    public abstract boolean canPassThrough(List<Gimul> gimuls);

    public boolean isSameTeam(Gimul other){
        return this.team.equals(other.team);
    }
}
