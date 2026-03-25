package domain.piece;

import domain.strategy.MoveStrategy;
import domain.PieceProperty;
import domain.PieceType;
import domain.Team;

public class Soldier extends Piece {

    public Soldier(Team team, MoveStrategy moveStrategy) {
        super(PieceProperty.of(PieceType.SOLDIER,team), moveStrategy);
    }

    @Override
    public boolean isGeneral() {
        return false;
    }
}
