package domain.piece;

public class Ma implements Piece {
    private final Team team;

    public Ma(Team team) {
        this.team = team;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
