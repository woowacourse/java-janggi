package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.position.Position;
import janggi.domain.position.RawPosition;
import janggi.domain.position.RawRoute;
import janggi.domain.position.Route;
import janggi.domain.routePolicy.RoutePolicy;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public abstract class Piece {

    protected final Team team;
    protected Position position;
    protected PieceType pieceType;
    protected RoutePolicy movePolicy;

    public Piece(Team team, Position position, PieceType pieceType, RoutePolicy movePolicy) {
        this.team = team;
        this.position = position;
        this.pieceType = pieceType;
        this.movePolicy = movePolicy;
    }

    protected abstract Set<RawRoute> calculateRawRoutes();

    protected abstract Set<RawRoute> calculateAdditionalRawRoutesInPalace();

    public RoutePolicy getMovePolicy() {
        return movePolicy;
    }

    public boolean isSameType(final PieceType pieceType) {
        return this.pieceType == pieceType;
    }

    public void move(final Position position) {
        this.position = new Position(position.x(), position.y());
    }

    public Set<Route> calculateRoutes() {
        final Set<Route> routesInBoard = new HashSet<>();
        for (RawRoute rawRoute : calculateRawRoutes()) {
            Route route = getValidRoutes(rawRoute);
            if (route != null) {
                routesInBoard.add(getValidRoutes(rawRoute));
            }
        }

        for (RawRoute additionalRoute : calculateAdditionalRawRoutesInPalace()) {
            Route route = getValidRoutes(additionalRoute);
            if (route != null && route.isDestinationInPalace()) {
                routesInBoard.add(getValidRoutes(additionalRoute));
            }
        }
        return routesInBoard;
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

    public PieceType getPieceType() {
        return pieceType;
    }

    public Team getTeam() {
        return team;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Piece piece = (Piece) o;
        return team == piece.team && Objects.equals(position, piece.position) && pieceType == piece.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(team, position, pieceType);
    }
}
