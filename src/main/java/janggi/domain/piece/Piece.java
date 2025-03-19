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

    public void move(final Position position) {
        this.position = new Position(position.x(), position.y());
    }

    public Set<Route> calculateRoutes() {
        Set<Route> returnRoute = new HashSet<>();
        for (RawRoute rawRoute : calculateRawRoutes()) {
            try {
                List<Position> positions = new ArrayList<>();
                for (RawPosition rawPosition : rawRoute.rawPositions()) {
                    positions.add(new Position(rawPosition.x(), rawPosition.y()));
                }
                returnRoute.add(new Route(positions));
            } catch (IllegalArgumentException e) {
                continue;
            }
        }
        return returnRoute;
    }

    public boolean isSamePosition(final Position otherPosition) {
        return position.equals(otherPosition);
    }
}
