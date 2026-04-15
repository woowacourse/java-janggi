package domain.piece;

import domain.piece.rule.PieceRule;
import domain.piece.rule.SoldierRule;

public class Soldier extends Piece {

    private static final PieceRule RULE = new SoldierRule();

    public Soldier(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.SOLDIER;
    }

    @Override
    protected PieceRule rule() {
        return RULE;
    }
}
