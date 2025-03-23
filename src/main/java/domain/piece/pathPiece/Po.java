package domain.piece.pathPiece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import domain.board.PieceFinder;
import domain.piece.Piece;

public class Po extends UnlimitedPathPiece {

    public Po(Team team, Coordinate coordinate) {
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

        final var piecesInPath = pieceFinder.findPiecesIn(path.coordinates());
        if (piecesInPath.size() != 1) {
            return false;
        }

        final var podari = piecesInPath.getFirst();
        if (podari.isPo()) {
            return false;
        }

        final var isArrivalPo = pieceFinder.findAt(arrival)
            .map(Piece::isPo)
            .orElse(false);
        return !isArrivalPo;
    }

    @Override
    public Piece moveTo(final Coordinate arrival) {
        return new Po(team, arrival);
    }

    @Override
    public boolean isPo() {
        return true;
    }
}
