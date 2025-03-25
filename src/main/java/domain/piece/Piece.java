package domain.piece;

import domain.Coordinate;
import domain.Movement;
import domain.Team;
import domain.board.PieceSearcher;
import java.util.HashSet;
import java.util.Objects;
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
            Set<Coordinate> connections = coordinate.findCastleConnections();
            connections.stream()
                .map(coordinate::computeMovementTo)
                .forEach(movements::add);
        }

        return movements;
    }

    public abstract boolean canMove(Coordinate arrival, PieceSearcher pieceSearcher);

    public abstract Piece moveTo(Coordinate arrival);

    public final boolean isSameTeam(Piece piece) {
        return piece.team.equals(this.team);
    }

    public final boolean isTeam(Team team) {
        return this.team.equals(team);
    }

    public final boolean isAt(Coordinate coordinate) {
        return this.coordinate.equals(coordinate);
    }

    public final Team getTeam() {
        return team;
    }

    public final Coordinate getCoordinate() {
        return coordinate;
    }

    public boolean isPo() {
        return false;
    }

    @Override
    public final boolean equals(final Object o) {
        if (!(o instanceof final Piece piece)) {
            return false;
        }

        return team == piece.team && Objects.equals(coordinate, piece.coordinate)
            && Objects.equals(movements, piece.movements);
    }

    @Override
    public int hashCode() {
        int result = Objects.hashCode(team);
        result = 31 * result + Objects.hashCode(coordinate);
        result = 31 * result + Objects.hashCode(movements);
        return result;
    }
}
