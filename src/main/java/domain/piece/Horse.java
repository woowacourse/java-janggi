package domain.piece;

import domain.Team;
import domain.strategy.HorseStrategy;

public class Horse extends MoveablePiece {

    public Horse(Team team) {
        super(team, new HorseStrategy());
    }
}
