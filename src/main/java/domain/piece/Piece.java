package domain.piece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import domain.board.PieceFinder;
import java.util.HashSet;
import java.util.Set;

public abstract class Piece {

    protected final Team team;
    protected final Coordinate coordinate;

    private final Set<Movement> movements;

    public Piece(Team team, Coordinate coordinate, Set<Movement> movements) {
        this.team = team;
        this.coordinate = coordinate;
        this.movements = movements;
    }

    public final Set<Movement> movements() {
        Set<Movement> movements = new HashSet<>(this.movements);
        if (coordinate.isInCastle()) {
            Set<Movement> diagonalMovements = coordinate.getMovementsIfInCastle();
            movements.addAll(diagonalMovements);
        }

        return movements;
    }

    public abstract boolean canMove(Coordinate arrival, PieceFinder pieceFinder);

    public abstract Piece moveTo(Coordinate arrival);

    public final boolean isSameTeam(Piece piece) {
        return piece.team.equals(this.team);
    }

    public final boolean isTeam(Team team) {
        return this.team.equals(team);
    }

    public final Team getTeam() {
        return team;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean isPo() {
        return false;
    }
}
