package domain.piece.pathPiece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import domain.board.PieceFinder;
import domain.piece.Piece;

public class Cha extends UnlimitedPathPiece {

    public Cha(Team team, Coordinate coordinate) {
        super(
            team,
            coordinate,
            Movement.CROSS_MOVEMENTS
        );
    }

    @Override
    public boolean canMove(
        final Coordinate arrival,
        final PieceFinder pieceFinder
    ) {
        final var path = findPath(arrival);
        final var coordinates = path.coordinates();

        System.out.println("path.isReachable() = " + path.isReachable());
        System.out.println("path.coordinates() = " + path.coordinates());
        System.out.println("pieceFinder.nonePiecesIn(path.coordinates()) = " + pieceFinder.nonePiecesIn(path.coordinates()));

        return path.isReachable() && pieceFinder.nonePiecesIn(coordinates);
    }

    @Override
    public Piece moveTo(final Coordinate arrival) {
        return new Cha(team, arrival);
    }
}
