package domain.piece;

import domain.Team;
import domain.strategy.MoveStrategy;

public class Guard extends Piece {

    public Guard(Team team, MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }
}
