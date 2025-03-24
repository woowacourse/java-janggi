package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;
import janggi.domain.position.RawPosition;
import janggi.domain.position.RawRoute;
import janggi.domain.position.Route;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class Piece {

    protected final Team team;
    protected Position position;
    protected PieceType pieceType;

    public Piece(final Position position, final Team team) {
        this.position = position;
        this.team = team;
    }

    protected abstract Set<RawRoute> calculateRawRoutes();

    public boolean isSameType(PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public void move(final Position position) {
        this.position = new Position(position.x(), position.y());
    }

    public Set<Route> calculateRoutes() {
        final Set<Route> rawRoutes = new HashSet<>();
        for (RawRoute rawRoute : calculateRawRoutes()) {
            Route route = getValidRoutes(rawRoute);
            if (route != null) {
                rawRoutes.add(getValidRoutes(rawRoute));
            }
        }
        return rawRoutes;
    }

    private Route getValidRoutes(final RawRoute rawRoute) {
        try {
            final List<Position> positions = new ArrayList<>();
            putValidPositions(rawRoute, positions);
            return new Route(positions);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private void putValidPositions(final RawRoute rawRoute, final List<Position> positions) {
        for (RawPosition rawPosition : rawRoute.rawPositions()) {
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
