package janggi.domain.piece;

import janggi.domain.mouveRule.MoveRule;
import janggi.domain.mouveRule.SoldierMoveRule;

public class Soldier extends Piece {

    public Soldier(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.SOLDIER;
    }

    @Override
    public MoveRule moveRule() {
        return new SoldierMoveRule(getTeam());
    }
}
