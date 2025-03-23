package domain.piece.noPathPiece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import domain.piece.Piece;
import java.util.Set;

public class Jol extends NoPathPiece {

    public Jol(Coordinate coordinate) {
        super(
            Team.CHO,
            coordinate,
            Set.of(Movement.UP, Movement.LEFT, Movement.RIGHT)
        );
    }

    @Override
    public Piece moveTo(final Coordinate arrival) {
        return new Jol(arrival);
    }
}
