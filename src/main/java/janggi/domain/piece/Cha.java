package janggi.domain.piece;

import janggi.domain.PalaceTopology;
import janggi.domain.Side;
import janggi.domain.policy.ClearPathPolicy;

public class Cha extends LinearPiece {
    public Cha(Side side, PalaceTopology palaceTopology) {
        super(new ClearPathPolicy(), palaceTopology, side, PieceType.CHA);
    }
}