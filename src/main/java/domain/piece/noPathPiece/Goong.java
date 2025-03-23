package domain.piece.noPathPiece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import domain.piece.Piece;

public class Goong extends NoPathPiece {

    public Goong(Team team, Coordinate coordinate) {
        super(
            team,
            coordinate,
            Movement.CROSS_MOVEMENTS
        );
    }

    @Override
    public Piece moveTo(final Coordinate arrival) {
        return new Goong(team, arrival);
    }
}
