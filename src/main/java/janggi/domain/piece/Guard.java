package janggi.domain.piece;

import janggi.domain.game.Team;

public final class Guard extends PalacePiece {

    public Guard(final Team team) {
        super(team);
    }

    @Override
    public int point() {
        return 3;
    }

    @Override
    public Type type() {
        return Type.GUARD;
    }
}
