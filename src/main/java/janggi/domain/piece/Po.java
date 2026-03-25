package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.strategy.PoMoveStrategy;

public class Po extends LinearPiece {
    public Po(Side side) {
        super(new PoMoveStrategy(), side);
    }
}
