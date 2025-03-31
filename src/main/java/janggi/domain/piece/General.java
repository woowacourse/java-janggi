package janggi.domain.piece;

import janggi.domain.game.Team;

public final class General extends PalacePiece {

    private final Team team;

    public General(final Team team) {
        this.team = team;
    }

    @Override
    public int point() {
        return 0;
    }

    @Override
    public Type type() {
        return Type.GENERAL;
    }

    @Override
    public Team team() {
        return team;
    }
}
