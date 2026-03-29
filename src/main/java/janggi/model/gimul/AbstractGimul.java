package janggi.model.gimul;

import janggi.model.Team;
import janggi.model.position.MoveResult;
import janggi.model.position.Position;
import java.util.List;

public abstract class AbstractGimul {
    protected final Team team;

    protected AbstractGimul(Team team) {
        this.team = team;
    }

    public abstract MoveResult getLegalPath(Position from, Position to);

    public abstract boolean canPassThrough(List<AbstractGimul> gimulsOnPath, AbstractGimul abstractGimulAtTo);

    public abstract boolean canPassThrough(List<AbstractGimul> gimulsOnPath);

    public boolean isSameTeam(AbstractGimul other) {
        return this.team.equals(other.team);
    }

    public boolean isSameTeam(Team other) {
        return this.team.equals(other);
    }
}
