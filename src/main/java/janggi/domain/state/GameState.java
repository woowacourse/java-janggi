package janggi.domain.state;

import janggi.domain.Side;
import janggi.domain.piece.Piece;

public interface GameState {
    boolean isEnd();

    void update(GameContext context, Piece piece);

    Side getCurrentSide();

    Side getWinner();
}
