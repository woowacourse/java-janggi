package janggi.domain.player;

import janggi.domain.Side;
import janggi.domain.board.Formation;

public record Player(Name name, Side side, Formation formation) {
}
