package janggi.model.gimul;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import java.util.List;
import java.util.Optional;

public abstract class AbstractGimul {
    protected final Team team;

    public AbstractGimul(Team team) {
        this.team = team;
    }

    public abstract PositionPath getLegalPath(Position from, Position to);

    public abstract boolean canPassThrough(List<AbstractGimul> gimulsOnPath, Optional<AbstractGimul> gimulAtTo);

    public boolean isSameTeam(AbstractGimul other) {
        return this.team.equals(other.team);
    }

    public boolean isSameTeam(Team other) {
        return this.team.equals(other);
    }

    public abstract String getSymbol();

    public abstract Score getScore();

    public Team getTeam() {
        return team;
    }
}
