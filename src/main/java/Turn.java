import domain.piece.Team;

public class Turn {
    private Team team;

    public Turn(Team team) {
        this.team = team;
    }

    public void changeTurn() {
        this.team = team.inverse();
    }

    public Team team() {
        return team;
    }
}
