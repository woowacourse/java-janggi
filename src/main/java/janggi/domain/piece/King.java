package janggi.domain.piece;

import janggi.domain.mouveRule.KingMoveRule;
import janggi.domain.mouveRule.MoveRule;

public class King extends Piece {

    public King(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.KING;
    }

    @Override
    public MoveRule moveRule() {
        return new KingMoveRule();
    }
}
