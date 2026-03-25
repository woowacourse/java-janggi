package domain;

public class Chariot implements Piece {

    private final Team team;

    private Chariot(final Team team) {
        this.team = team;
    }

    public static Chariot of(final Team team) {
        return new Chariot(team);
    }

    public Team findMyTeam() {
        return team.getNation();
    }

    @Override
    public void move(

    ) {

    }
}
