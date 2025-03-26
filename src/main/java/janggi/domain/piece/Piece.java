package janggi.domain.piece;

import java.util.Map;
import java.util.function.Consumer;

public abstract class Piece {
    protected final Team team;
    private final String name;

    public Piece(final String name, final Team team) {
        this.name = name;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public abstract Consumer<Map<Position, Piece>> getMovableValidator(
            final Position beforePosition,
            final Position afterPosition
    );

    public boolean isCannon() {
        return false;
    }

    public boolean isNone() {
        return false;
    }
}