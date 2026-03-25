package janggi.domain.piece;

import janggi.domain.mouveRule.MoveRule;

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
        return null;
    }

}
