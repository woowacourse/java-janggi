package domain.piece;

import domain.Team;
import domain.strategy.MoveStrategy;

public class Chariot extends Piece {

    public Chariot(Team team, MoveStrategy moveStrategy) {
        super(team, moveStrategy);
    }
}
