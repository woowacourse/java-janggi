package janggi.piece;

public class Piece {

    private final String team;

    public Piece(String team) {
        this.team = team;
    }

    public boolean checkConuntry(String team) {
        return this.team.equals(team);
    }
}
