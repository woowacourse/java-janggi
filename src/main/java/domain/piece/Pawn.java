package domain.piece;

import domain.Team;
import domain.strategy.MoveStrategy;


public class Pawn extends Piece {

    public Pawn(Team team, MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }

    @Override
    public PieceType getPieceType() {
        return  PieceType.PAWN;
    }

    @Override
    public boolean isBridge() {
        return true;
    }

    @Override
    public boolean isCatchByCannon() {
        return true;
    }

    @Override
    public boolean isBlank() {
        return false;
    }
}
