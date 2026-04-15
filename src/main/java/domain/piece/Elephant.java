package domain.piece;

import domain.piece.rule.ElephantRule;
import domain.piece.rule.PieceRule;

public class Elephant extends Piece {

    private static final PieceRule RULE = new ElephantRule();

    public Elephant(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.ELEPHANT;
    }

    @Override
    protected PieceRule rule() {
        return RULE;
    }
}
