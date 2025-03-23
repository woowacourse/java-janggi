package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Piece {

    protected Position position;
    protected final Team team;

    public abstract Set<Route> calculateRoutes();

    public Set<Route> getPossibleRoutes(final List<Piece> otherPieces) {
        return calculateRoutes().stream()
                .filter(route -> isValidRoute(route, otherPieces))
                .collect(Collectors.toSet());
    }

    protected boolean isValidRoute(final Route route, final List<Piece> otherPieces) {

        final Position destination = route.getDestination();

        final Optional<Piece> pieceAtDestination = otherPieces.stream()
                .filter(p -> p.isSamePosition(destination))
                .findFirst();

        return pieceAtDestination.isEmpty() || isEnemy(pieceAtDestination.get());
    }
    
    public Piece(final Position position, final Team team) {
        this.position = position;
        this.team = team;
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
}
