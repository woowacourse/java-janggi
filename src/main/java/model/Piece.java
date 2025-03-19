package model;

public abstract class Piece {
    Team team;

    public Piece(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

    public abstract boolean isValidPoint(int beforeX, int beforeY, int afterX, int afterY);

    public abstract Path calculatePath(int beforeX, int beforeY, int afterX, int afterY);

    public abstract boolean canMove(int size);
}
