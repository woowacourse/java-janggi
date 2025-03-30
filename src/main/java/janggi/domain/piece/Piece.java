package janggi.domain.piece;

import janggi.domain.game.Board;
import janggi.domain.position.Position;
import janggi.domain.game.Team;

public interface Piece {

    void validateMove(final Position source, final Position destination,
                      final Board board);

    int point();

    Type type();

    Team team();
}
