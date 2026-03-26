package domain.piece;

import domain.Team;
import domain.strategy.MoveStrategy;
import domain.PieceProperty;
import domain.PieceType;

public class None extends Piece {

    public None(Team team, MoveStrategy moveStrategy) {
        super(PieceProperty.of(PieceType.EMPTY_VALUE,team), moveStrategy);
    }

    @Override
    public boolean isGeneral() {
        return false;
    }
}
