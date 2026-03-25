package domain.piece;

import domain.strategy.MoveStrategy;
import domain.PieceProperty;
import domain.PieceType;
import domain.Team;

public class General extends Piece {

    public General(Team team, MoveStrategy moveStrategy) {
        super(PieceProperty.of(PieceType.GENERAL,team), moveStrategy);
    }

    @Override
    public boolean isGeneral() {
        return true;
    }
}
