package janggi.model.gimul;

import janggi.model.Score;
import janggi.model.Team;
import janggi.model.position.Position;
import janggi.model.position.PositionPath;
import java.util.List;
import java.util.Optional;

public abstract class AbstractGimul {
    protected final Team team;
    protected final GimulType type;

    public AbstractGimul(Team team, GimulType type) {
        this.team = team;
        this.type = type;
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

    public abstract boolean canBeJumpedOver();

    public abstract boolean isKing();

    public Team getTeam() {
        return team;
    }

    public GimulType getType() {
        return type;
    }
}
