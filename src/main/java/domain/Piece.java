package domain;

public class Piece {
    private final Position position;
    private final Team team;

    public Piece(Position position, Team team) {
        this.position = position;
        this.team = team;
    }

    public Position getPosition() {
        return position;
    }

    public Team getTeam() {
        return team;
    }
}
