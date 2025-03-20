package janggi.domain.piece;

import janggi.domain.RawRoute;
import janggi.domain.Route;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Piece {

    protected Position position;
    protected final Team team;

    protected abstract Set<RawRoute> calculateRawRoutes();

    public Piece(final Position position, final Team team) {
        this.position = position;
        this.team = team;
    }

    public abstract boolean isCannon();

    public void move(final Position position) {
        this.position = new Position(position.x(), position.y());
    }

    public Set<Route> calculateRoutes() {
        final Set<Route> rawRoutes = new HashSet<>();
        for (final RawRoute rawRoute : calculateRawRoutes()) {
            putValidRoutes(rawRoute, rawRoutes);
        }
        return rawRoutes;
    }

    private void putValidRoutes(final RawRoute rawRoute, final Set<Route> returnRoute) {
        try {
            final List<Position> positions = new ArrayList<>();
            putValidPositions(rawRoute, positions);
            returnRoute.add(new Route(positions));
        } catch (final IllegalArgumentException e) {
        }
    }

    private void putValidPositions(final RawRoute rawRoute, final List<Position> positions) {
        for (final RawPosition rawPosition : rawRoute.rawPositions()) {
            positions.add(new Position(rawPosition.x(), rawPosition.y()));
        }
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
}
