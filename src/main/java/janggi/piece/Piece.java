package janggi.piece;

import janggi.score.Score;
import janggi.game.Team;
import janggi.movement.middleRoute.Hurdles;
import janggi.point.Point;

public abstract class Piece {

    protected static final int MAX_COLUMN_LOCATION = 9;

    protected final Team team;
    protected final Point point;
    private final PieceInformation information;

    public Piece(Team team, Point point, PieceInformation information) {
        this.team = team;
        this.point = point;
        this.information = information;
    }

    public abstract boolean canMove(Point targetPoint, Hurdles hurdles);

    public abstract Piece updatePoint(Point afterPoint);

    public boolean isPo() {
        return false;
    }

    public boolean isGung() {
        return false;
    }

    public Point getPoint() {
        return point;
    }

    public Team getTeam() {
        return team;
    }

    public String getName() {
        return information.getName();
    }

    public Score getScore() {
        return information.getScore();
    }

    public PieceInformation getType() {
        return information;
    }
}
