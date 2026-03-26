package janggi.domain.piece;

import janggi.domain.mouveRule.HorseMoveRule;
import janggi.domain.mouveRule.MoveRule;

public class Horse extends Piece {

    public Horse(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.HORSE;
    }

    @Override
    public MoveRule moveRule() {
        return new HorseMoveRule();
    }

}
