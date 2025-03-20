package model;

import java.util.Map;

public abstract class Piece {
    Team team;
    PieceName pieceName;

    public Piece(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public abstract boolean isValidPoint(Point beforePoint, Point targetPoint);

    public abstract Path calculatePath(Point beforePoint, Point targetPoint);

    public abstract boolean canMove(Map<Piece, Boolean> piecesOnPathWithTargetOrNot);

    public PieceName getPieceName() {
        return pieceName;
    }
}
