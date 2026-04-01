package domain.piece;

import domain.Team;
import domain.strategy.MoveStrategy;

public class King extends Piece {

    public King(Team team, MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }
}
