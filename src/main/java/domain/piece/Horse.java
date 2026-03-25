package domain.piece;

import domain.strategy.MoveStrategy;
import domain.PieceProperty;
import domain.PieceType;
import domain.Team;

public class Horse extends Piece {

    public Horse(Team team, MoveStrategy moveStrategy) {
        super(PieceProperty.of(PieceType.HORSE,team), moveStrategy);
    }

    @Override
    public boolean isGeneral() {
        return false;
    }
}
