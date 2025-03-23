package domain.piece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import domain.board.PieceFinder;
import java.util.List;
import java.util.Set;

public abstract class Piece {

    protected final Team team;
    protected final Set<Movement> movements;

    public Piece(Team team, Set<Movement> movements) {
        this.team = team;
        this.movements = movements;
    }

    public final boolean canMove(PieceFinder pieceFinder, Coordinate departure, Coordinate arrival) {
        if (!findMovableCandidates(departure).contains(arrival)) {
            return false;
        }
        return canMoveConsideringObstacles(pieceFinder, departure, arrival);
    }

    protected abstract Set<Coordinate> findMovableCandidates(Coordinate departure);

    protected boolean canMoveConsideringObstacles(PieceFinder pieceFinder, Coordinate departure, Coordinate arrival) {
        final var path = findPaths(departure, arrival);
        return pieceFinder.nonePiecesIn(path);
    }

    protected abstract List<Coordinate> findPaths(Coordinate departure, Coordinate arrival);

    public final boolean isSameTeam(Piece piece) {
        return piece.team.equals(this.team);
    }

    public final boolean isTeam(Team team) {
        return this.team.equals(team);
    }

    public final Team getTeam() {
        return team;
    }

    public boolean isPo() {
        return false;
    }
}
