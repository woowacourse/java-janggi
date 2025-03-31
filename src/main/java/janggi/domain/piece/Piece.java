package janggi.domain.piece;

import janggi.domain.game.Board;
import janggi.domain.game.Team;
import janggi.domain.position.Position;

public abstract class Piece {

    private final Team team;

    public Piece(final Team team) {
        this.team = team;
    }


    public abstract void validateMove(final Position source, final Position destination,
                                      final Board board);

    public abstract int point();

    public abstract Type type();

    public Team team() {
        return team;
    }
}
