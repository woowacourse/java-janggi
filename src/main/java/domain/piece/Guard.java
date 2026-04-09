package domain.piece;

import domain.Team;
import domain.strategy.PalaceStrategy;

public class Guard extends MoveablePiece {

    public Guard(Team team) {
        super(team, new PalaceStrategy());
    }
}
