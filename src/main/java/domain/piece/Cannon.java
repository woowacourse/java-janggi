package domain.piece;

import domain.Team;
import domain.strategy.MoveStrategy;


public class Cannon extends Piece {

    public Cannon(Team team, MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.CANNON;
    }

    @Override
    public boolean isBridge() {
        return false;
    }

    @Override
    public boolean isCatchByCannon() {
        return false;
    }

    @Override
    public boolean isBlank() {
        return false;
    }
}
