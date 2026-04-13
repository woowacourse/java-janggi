package domain.piece;

import domain.piece.rule.ChariotRule;
import domain.piece.rule.PieceRule;

public class Chariot extends Piece {

    private static final PieceRule RULE = new ChariotRule();

    public Chariot(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CHARIOT;
    }

    @Override
    protected PieceRule rule() {
        return RULE;
    }
}
