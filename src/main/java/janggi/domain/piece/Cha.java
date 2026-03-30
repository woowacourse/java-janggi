package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;

public class Cha extends LinearPiece {
    public Cha(Side side) {
        super(new ClearPathPolicy(), side, PieceType.CHA);
    }
}