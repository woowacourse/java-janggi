package domain.piece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import java.util.List;

public class Sa extends LimitedMovementPiece {

    public Sa(Team team) {
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
