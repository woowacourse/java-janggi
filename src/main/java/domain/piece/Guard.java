package domain.piece;

import domain.strategy.MoveStrategy;
import domain.PieceProperty;
import domain.PieceType;
import domain.Team;

public class Guard extends Piece {

    public Guard(Team team, MoveStrategy moveStrategy) {
        super(PieceProperty.of(PieceType.GUARD, team), moveStrategy);
    }

    @Override
    public boolean isGeneral() {
        return false;
    }
}
