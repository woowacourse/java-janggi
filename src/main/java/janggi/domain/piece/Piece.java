package janggi.domain.piece;

import janggi.domain.point.Point;
import janggi.domain.point.Points;
import janggi.domain.point.Route;
import janggi.domain.status.Team;

public abstract class Piece {

    private final Team team;
    private final PieceType type;
    private final int score;

    public Piece(int score, Team team, PieceType type) {
        this.score = score;
        this.team = team;
        this.type = type;
    }

    public abstract Points getRoutePoints(Point from, Point to);

    public boolean canCapture(Piece target) {
        return true;
    }

    public boolean canMove(Route route) {
        return route.isEmpty();
    }

    public boolean isSameTeam(Team team) {
        return this.team.equals(team);
    }

    public boolean isSameType(PieceType type) {
        return this.type.equals(type);
    }

    public PieceType getType() {
        return type;
    }

    public int getScore() {
        return score;
    }

    protected Team getTeam() {
        return this.team;
    }
}
