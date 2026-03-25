package domain.piece;

import domain.strategy.MoveStrategy;
import domain.PieceProperty;
import domain.PieceType;
import domain.Team;

public class Cannon extends Piece {

    public Cannon(Team team, MoveStrategy moveStrategy) {
        super(PieceProperty.of(PieceType.CANNON,team), moveStrategy);
    }

    @Override
    public boolean isGeneral() {
        return false;
    }
}
