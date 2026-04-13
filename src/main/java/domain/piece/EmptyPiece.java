package domain.piece;

import domain.piece.rule.EmptyPieceRule;
import domain.piece.rule.PieceRule;

public class EmptyPiece extends Piece {

    private static final PieceRule RULE = new EmptyPieceRule();

    public EmptyPiece(Team team) {
        super(team);
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public PieceType pieceType() {
        return PieceType.EMPTY;
    }

    @Override
    protected PieceRule rule() {
        return RULE;
    }
}
