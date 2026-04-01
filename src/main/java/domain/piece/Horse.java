package domain.piece;

import domain.Team;
import domain.strategy.MoveStrategy;


public class Horse extends Piece {

    public Horse(Team team, MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }

}
