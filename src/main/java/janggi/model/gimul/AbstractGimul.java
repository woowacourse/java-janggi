package janggi.model.gimul;

import janggi.model.Team;
import janggi.model.board.PositionPath;
import janggi.model.board.position.Position;
import java.util.List;

public abstract class AbstractGimul {
    protected final Team team;

    protected AbstractGimul(Team team) {
        this.team = team;
    }

    public abstract PositionPath getLegalPath(Position from, Position to);

    public abstract boolean canPassThrough(List<AbstractGimul> gimulsOnPath, AbstractGimul abstractGimulAtTo);

    public abstract boolean canPassThrough(List<AbstractGimul> gimulsOnPath);

    public boolean isSameTeam(AbstractGimul other) {
        return this.team.equals(other.team);
    }

    public boolean isSameTeam(Team other) {
        return this.team.equals(other);
    }
}
