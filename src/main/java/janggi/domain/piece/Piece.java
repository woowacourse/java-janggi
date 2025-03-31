package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class Piece {

    protected Position position;
    protected final Team team;

    public Piece(final Position position, final Team team) {
        this.position = position;
        this.team = team;
    }

    public abstract Set<Route> calculateIndependentRoutes();

    public abstract boolean isValidRoute(final Route route, final List<Piece> otherPieces);

    public abstract double getScore();

    public abstract PieceType getPieceType();

    public Set<Route> getPossibleRoutes(final List<Piece> otherPieces) {
        final Set<Route> routes = new HashSet<>();
        for (final Route route : calculateIndependentRoutes()) {
            if (isValidRoute(route, otherPieces)) {
                routes.add(route);
            }
        }
        return routes;
    }

    public void move(final Position position) {
        this.position = new Position(position.x(), position.y());
    }

    public boolean isSamePosition(final Position otherPosition) {
        return position.equals(otherPosition);
    }

    public boolean isSameTeam(final Team otherTeam) {
        return team == otherTeam;
    }

    public boolean isEnemy(final Piece otherPiece) {
        return team != otherPiece.team;
    }

    protected boolean isCannon() {
        return false;
    }

    public boolean isGeneral() {
        return false;
    }

    public Position getPosition() {
        return position;
    }

    public Team getTeam() {
        return team;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Piece piece = (Piece) o;
        return Objects.equals(position, piece.position) && team == piece.team;
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, team);
    }
}
