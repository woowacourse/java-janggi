package janggi.piece;

import janggi.game.Board;
import janggi.game.Team;
import janggi.position.Position;

public interface Piece {

    public abstract void validateMove(final Position source, final Position destination,
                                      final Board board);

    public abstract int point();

    public abstract Type type();

    public abstract Team team();
}
