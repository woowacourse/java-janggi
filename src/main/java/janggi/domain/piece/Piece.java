package janggi.domain.piece;

import java.util.Map;

public abstract class Piece {
    protected final Team team;
    private final Position position;
    private final String name;

    public Piece(final String name, final Position position, final Team team) {
        this.name = name;
        this.position = position;
        this.team = team;
    }

    public Position getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public boolean isNone() {
        return false;
    }

    public boolean isNotNone() {
        return !isNone();
    }

    public abstract Piece move(final Map<Position, Piece> pieces, final Position positionToMove);
}
