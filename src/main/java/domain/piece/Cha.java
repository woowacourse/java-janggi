package domain.piece;

import domain.Movement;
import domain.Team;

public class Cha extends UnlimitedStraightMovingPiece {

    public Cha(Team team) {
        super(
            team,
            Movement.CROSS_MOVEMENTS
        );
    }
}
