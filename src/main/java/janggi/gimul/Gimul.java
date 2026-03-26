package janggi.gimul;

import janggi.position.PositionPath;
import janggi.position.Position;
import janggi.Team;
import java.util.List;

public abstract class Gimul {
    protected final Team team;

    public Gimul(Team team) {
        this.team = team;
    }

    public abstract PositionPath getLegalPath(Position from, Position to);
    public abstract boolean canPassThrough(List<Gimul> gimulsOnPath, Gimul gimulAtTo);

    public boolean isSameTeam(Gimul other){
        return this.team.equals(other.team);
    }
    public boolean isSameTeam(Team other) {
        return this.team.equals(other);
    }
}
