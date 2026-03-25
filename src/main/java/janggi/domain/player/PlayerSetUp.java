package janggi.domain.player;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.side.Side;

public record PlayerSetUp(
        Player player,
        Side side,
        BoardSetUp boardSetUp
) {
}
