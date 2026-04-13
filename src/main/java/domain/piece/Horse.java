package domain.piece;

import domain.piece.rule.HorseRule;
import domain.piece.rule.PieceRule;

public class Horse extends Piece {

    private static final PieceRule RULE = new HorseRule();

    public Horse(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.HORSE;
    }

    @Override
    protected PieceRule rule() {
        return RULE;
    }
}
