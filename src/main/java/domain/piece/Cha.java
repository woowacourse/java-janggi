package domain.piece;

import domain.Movement;
import domain.Team;

public class Cha extends UnlimitedCrossMovementPiece {

    public Cha(Team team) {
        super(
            team,
            Movement.CROSS_MOVEMENTS
        );
    }
}
