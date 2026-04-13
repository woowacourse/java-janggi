package domain.piece;

import domain.Team;
import domain.strategy.MoveStrategy;


public class Horse extends Piece {

    public Horse(Team team, MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.HORSE;
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
