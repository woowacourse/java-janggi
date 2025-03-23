package domain.piece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import java.util.List;
import java.util.Set;

public class Byeong extends LimitedMovementPiece{

    public Byeong() {
        super(
            Team.HAN,
            Set.of(Movement.DOWN, Movement.LEFT, Movement.RIGHT)
        );
    }

    @Override
    protected List<Coordinate> findPaths(final Coordinate departure, final Coordinate arrival) {
        return List.of();
    }
}
