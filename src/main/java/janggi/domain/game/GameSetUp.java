package janggi.domain.game;

import janggi.domain.board.setup.BoardSetUp;
import janggi.domain.side.Side;

public record GameSetUp(
        Side side,
        BoardSetUp boardSetUp
) {

}
