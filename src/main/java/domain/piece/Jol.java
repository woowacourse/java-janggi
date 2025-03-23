package domain.piece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import java.util.List;
import java.util.Set;

public class Jol extends LimitedMovementPiece {

    public Jol() {
        super(
            Team.CHO,
            Set.of(Movement.UP, Movement.LEFT, Movement.RIGHT)
        );
    }

    @Override
    protected List<Coordinate> findPaths(Coordinate departure, Coordinate arrival) {
        return List.of();
    }
}
