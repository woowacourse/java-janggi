package domain.piece;

import domain.strategy.MoveStrategy;
import domain.PieceProperty;
import domain.PieceType;
import domain.Team;

public class Elephant extends Piece {

    public Elephant(Team team, MoveStrategy moveStrategy) {
        super(PieceProperty.of(PieceType.ELEPHANT,team), moveStrategy);
    }

    @Override
    public boolean isGeneral() {
        return false;
    }
}
