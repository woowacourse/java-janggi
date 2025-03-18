package model;

public abstract class Piece {
    String team;

    public Piece(String team) {
        this.team = team;
    }

    public String getTeam() {
        return team;
    }

    public abstract boolean canMove(int beforeX, int beforeY, int afterX, int afterY);

    public abstract Path calculatePath(int beforeX, int beforeY, int afterX, int afterY);
}
