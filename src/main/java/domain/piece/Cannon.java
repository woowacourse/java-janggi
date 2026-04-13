package domain.piece;

import domain.piece.rule.CannonRule;
import domain.piece.rule.PieceRule;

public class Cannon extends Piece {

    private static final CannonRule RULE = new CannonRule();

    public Cannon(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.CANNON;
    }

    @Override
    protected PieceRule rule() {
        return RULE;
    }

    @Override
    public void validateNotSameTeam(Piece piece) {
        RULE.validateTarget(piece);
        super.validateNotSameTeam(piece);
    }
}
