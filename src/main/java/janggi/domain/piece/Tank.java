package janggi.domain.piece;

import janggi.domain.mouveRule.MoveRule;
import janggi.domain.mouveRule.TankMoveRule;

public class Tank extends Piece {

    public Tank(Team team) {
        super(team);
    }

    @Override
    public PieceType pieceType() {
        return PieceType.TANK;
    }

    @Override
    public MoveRule moveRule() {
        return new TankMoveRule();
    }
}
