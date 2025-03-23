package domain.piece.pathPiece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import domain.board.PieceFinder;
import domain.piece.Piece;
import java.util.Set;

public abstract class PathPiece extends Piece {

    public PathPiece(
        final Team team,
        final Coordinate coordinate,
        final Set<Movement> movements)
    {
        super(team, coordinate, movements);
    }

    @Override
    public boolean canMove(
        final Coordinate arrival,
        final PieceFinder pieceFinder
    ) {
        final var path = findPath(arrival);
        final var coordinates = path.coordinates();

        return path.isReachable() && pieceFinder.nonePiecesIn(coordinates);
    }

    protected abstract Path findPath(Coordinate arrival);
}
