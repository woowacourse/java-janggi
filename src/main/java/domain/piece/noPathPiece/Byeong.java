package domain.piece.noPathPiece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import domain.piece.Piece;
import java.util.Set;

public class Byeong extends NoPathPiece {

    public Byeong(Coordinate coordinate) {
        super(
            Team.HAN,
            coordinate,
            Set.of(Movement.DOWN, Movement.LEFT, Movement.RIGHT)
        );
    }

    @Override
    public Piece moveTo(final Coordinate arrival) {
        return new Byeong(arrival);
    }
}
