package domain.piece;

import domain.Team;
import domain.strategy.PalaceStrategy;

public class King extends MoveablePiece {

    public King(Team team) {
        super(team, new PalaceStrategy());
    }
}
