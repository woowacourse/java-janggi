package janggi.domain;

import janggi.domain.moveRules.MoveRule;
import janggi.domain.moveRules.outofpalacemoverules.JumpMoveRule;
import janggi.domain.moveRules.palacemoverules.ChaPalaceMoveRule;
import janggi.domain.moveRules.palacemoverules.KingAndSaPalaceMoveRule;
import janggi.domain.moveRules.palacemoverules.PoPalaceMoveRule;
import janggi.domain.moveRules.palacemoverules.ZolPalaceMoveRule;

public enum PieceType {
    KING(new KingAndSaPalaceMoveRule(), 0.0),
    SA(new KingAndSaPalaceMoveRule(), 3.0),
    SANG(new JumpMoveRule(), 3.0),
    MA(new JumpMoveRule(), 5.0),
    CHA(new ChaPalaceMoveRule(), 13.0),
    PO(new PoPalaceMoveRule(), 7.0),
    ZOL(new ZolPalaceMoveRule(), 2.0);

    private final MoveRule moveRule;
    private final double score;

    PieceType(MoveRule moveRule, double score) {
        this.moveRule = moveRule;
        this.score = score;
    }

    public MoveRule getMoveRule() {
        return moveRule;
    }

    public double getScore() {
        return score;
    }
}
