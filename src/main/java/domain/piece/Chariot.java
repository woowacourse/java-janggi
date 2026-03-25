package domain.piece;

import domain.strategy.MoveStrategy;
import domain.PieceProperty;
import domain.PieceType;
import domain.Team;

public class Chariot extends Piece {

    public Chariot(Team team, MoveStrategy moveStrategy) {
        super(PieceProperty.of(PieceType.CHARIOT,team), moveStrategy);
    }

    @Override
    public boolean isGeneral() {
        return false;
    }
}
