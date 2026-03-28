package janggi.domain.piece;

import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.point.Route;
import janggi.domain.status.Team;

abstract class AbstractPiece implements Piece {

    private final Team team;
    private final PieceType type;

    public AbstractPiece(Team team, PieceType type) {
        this.team = team;
        this.type = type;
    }

    @Override
    public abstract Points getRoutePoints(Point from, Point to);

    @Override
    public boolean canCapture(Piece target) {
        return true;
    }

    @Override
    public boolean canMove(Route route) {
        return route.isEmpty();
    }

    @Override
    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    @Override
    public boolean isSameType(PieceType type) {
        return this.type.equals(type);
    }

    @Override
    public PieceType getType() {
        return type;
    }

    protected Team getTeam() {
        return this.team;
    }
}
