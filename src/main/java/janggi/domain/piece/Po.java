package janggi.domain.piece;

import janggi.domain.Side;
import janggi.domain.policy.JumpPolicy;

public class Po extends LinearPiece {
    public Po(Side side) {
        super(new JumpPolicy(), side, PieceType.PO);
    }

    @Override
    public boolean isPo(){
        return true;
    }
}