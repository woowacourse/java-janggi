package janggi.domain.piece;

import janggi.domain.game.Team;

public final class General extends PalacePiece {

    public General(final Team team) {
        super(team);
    }

    @Override
    public int point() {
        return 0;
    }

    @Override
    public Type type() {
        return Type.GENERAL;
    }
}
