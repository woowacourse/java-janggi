package domain.piece;

import domain.piece.rule.GuardRule;
import domain.piece.rule.PieceRule;

public class Guard extends Piece {

    private static final PieceRule RULE = new GuardRule();

    public Guard(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.GUARD;
    }

    @Override
    protected PieceRule rule() {
        return RULE;
    }
}
