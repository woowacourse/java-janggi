package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Piece {

    protected Position position;
    protected final Team team;

    public Piece(final Position position, final Team team) {
        this.position = position;
        this.team = team;
    }

    public abstract Set<Route> calculateIndependentRoutes();

    public Set<Route> getPossibleRoutes(final List<Piece> otherPieces) {
        return calculateIndependentRoutes().stream()
                .filter(route -> isValidRoute(route, otherPieces))
                .collect(Collectors.toSet());
    }

    protected boolean isValidRoute(final Route route, final List<Piece> otherPieces) {
        final List<Piece> piecesInRoute = otherPieces.stream()
                .filter(route::hasPosition)
                .toList();
        return checkPiecesInRoute(route, piecesInRoute);
    }

    private boolean checkPiecesInRoute(final Route route, final List<Piece> piecesInRoute) {
        if (piecesInRoute.isEmpty()) {
            return true;
        }
        if (piecesInRoute.size() == 1) {
            final Piece pieceInWay = piecesInRoute.getFirst();
            return route.isDestination(pieceInWay) && isEnemy(pieceInWay);
        }
        return piecesInRoute.stream()
                .allMatch(piece -> route.isDestination(piece) && isEnemy(piece));
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
}
