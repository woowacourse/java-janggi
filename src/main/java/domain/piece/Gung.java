package domain.piece;

public class Gung implements Piece {
    private final Team team;

    public Gung(Team team) {
        this.team = team;
    }

    @Override
    public boolean isPho() {
        return false;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
