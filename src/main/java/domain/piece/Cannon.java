package domain.piece;

import domain.Team;
import domain.strategy.CannonStrategy;

public class Cannon extends MoveablePiece {

    public Cannon(Team team) {
        super(team, new CannonStrategy());
    }
}
