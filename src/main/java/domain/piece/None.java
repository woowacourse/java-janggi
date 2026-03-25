package domain.piece;

import domain.strategy.MoveStrategy;
import domain.PieceProperty;
import domain.PieceType;
import domain.Team;

public class None extends Piece {

    public None(Team team, MoveStrategy moveStrategy) {
        super(PieceProperty.of(PieceType.EMPTY_VALUE,team), moveStrategy);
    }

    @Override
    public boolean isGeneral() {
        return false;
    }
}
