package janggi.piece;

import janggi.game.Board;
import janggi.position.Position;
import janggi.game.Team;

public interface Piece {

    public abstract void validateMove(final Position source, final Position destination,
                                      final Board board);

    public abstract Type type();

    public abstract Team team();
}
