package janggi.domain.piece;

import janggi.domain.PalaceTopology;
import janggi.domain.Side;
import janggi.domain.policy.JumpPolicy;

public class Po extends LinearPiece {
    public Po(Side side, PalaceTopology palaceTopology) {
        super(new JumpPolicy(), palaceTopology, side, PieceType.PO);
    }
}