package janggi.temp.piece;

import janggi.temp.game.Board;
import janggi.temp.game.Team;
import janggi.temp.position.Position;

public interface Piece {

    public abstract void validateMove(final Position source, final Position destination,
                                      final Board board);

    public abstract Type type();

    public abstract Team team();
}
