package model;

public abstract class Piece {
    Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public abstract boolean isValidPoint(Point beforePoint, Point afterPoint);

    public abstract Path calculatePath(Point beforePoint, Point afterPoint);

    public abstract boolean canMove(int size);
}
