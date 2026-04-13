package domain.piece;

import domain.piece.rule.GeneralRule;
import domain.piece.rule.PieceRule;

public class General extends Piece {

    private static final PieceRule RULE = new GeneralRule();

    public General(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.GENERAL;
    }

    @Override
    public boolean isAliveGeneral() {
        return true;
    }

    @Override
    protected PieceRule rule() {
        return RULE;
    }
}
