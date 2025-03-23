package janggi.domain.piece;

import janggi.domain.Team;
import janggi.domain.piece.direction.Position;
import janggi.domain.piece.direction.Route;
import java.util.HashSet;
import java.util.Set;

public abstract class Piece {

    protected Position position;
    protected final Team team;

    protected abstract Set<Route> calculateRawRoutes();

    public Piece(final Position position, final Team team) {
        this.position = position;
        this.team = team;
    }

    public abstract boolean isCannon();

    public void move(final Position position) {
        this.position = new Position(position.x(), position.y());
    }

    public Set<Route> calculateRoutes() {
        return new HashSet<>(calculateRawRoutes());
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
