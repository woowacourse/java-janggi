package domain.piece;

import domain.Team;
import domain.strategy.MoveStrategy;

public class Elephant extends Piece {

    public Elephant(Team team, MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }
}
