package janggi.piece;

import janggi.game.Board;
import janggi.game.Team;
import janggi.position.Position;

public interface Piece {

    void validateMove(final Position source, final Position destination,
                      final Board board);

    int point();

    Type type();

    Team team();
}
