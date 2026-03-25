package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.strategy.DefaultMoveStrategy;

public class Cha extends LinearPiece {
    public Cha(Side side) {
        super(new DefaultMoveStrategy(), side);
    }
}