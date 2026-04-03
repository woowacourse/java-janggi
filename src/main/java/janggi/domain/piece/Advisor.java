package janggi.domain.piece;

import janggi.domain.mouveRule.AdvisorMoveRule;
import janggi.domain.mouveRule.MoveRule;

public class Advisor extends Piece {

    public Advisor(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.ADVISOR;
    }

    @Override
    public MoveRule moveRule() {
        return new AdvisorMoveRule();
    }
}
