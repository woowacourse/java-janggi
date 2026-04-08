package domain.game;

import domain.piece.Piece;
import domain.piece.Team;

public class Turn {
    private final Team team;

    public Turn(Team team) {
        this.team = team;
    }

    public String display() {
        return team.display();
    }

    public String colorCode(String red, String green) {
        return team.colorCode(red, green);
    }

    public Team team() {
        return team;
    }

    public boolean belongsTo(Piece piece) {
        return piece.isOwnedBy(team);
    }

    public Turn changeTeam() {
        return new Turn(team.enemy());
    }

    public Team getEnemy() {
        return team.enemy();
    }
}
