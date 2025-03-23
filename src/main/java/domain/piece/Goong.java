package domain.piece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import java.util.List;

public class Goong extends LimitedMovementPiece {

    public Goong(Team team) {
        super(
            team,
            Movement.CROSS_MOVEMENTS
        );
    }

    @Override
    protected List<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        return List.of();
    }
}
