package domain.piece;

import domain.Team;
import domain.strategy.MoveStrategy;

public class King extends Piece {

    public King(Team team, MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
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
