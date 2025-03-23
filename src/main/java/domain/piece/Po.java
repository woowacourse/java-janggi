package domain.piece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import domain.board.PieceFinder;
import java.util.Set;

public class Po extends UnlimitedCrossMovementPiece {

    public Po(Team team) {
        super(
            team,
            Set.of(Movement.UP, Movement.DOWN, Movement.LEFT, Movement.RIGHT)
        );
    }

    @Override
    protected boolean canMoveConsideringObstacles(PieceFinder pieceFinder, Coordinate departure,
        Coordinate arrival) {
        final var paths = findPaths(departure, arrival);

        final var piecesInPath = pieceFinder.findPiecesIn(paths);
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
    public boolean isPo() {
        return true;
    }
}
