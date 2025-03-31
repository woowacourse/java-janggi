package janggi.domain.piece;

import janggi.domain.game.Team;

public final class Guard extends PalacePiece {

    private final Team team;

    public Guard(final Team team) {
        this.team = team;
    }

    @Override
    public int point() {
        return 3;
    }

    @Override
    public Type type() {
        return Type.GUARD;
    }

    @Override
    public Team team() {
        return team;
    }
}
