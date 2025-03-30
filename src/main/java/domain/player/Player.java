package domain.player;

import domain.Team;
import domain.piece.Piece;

public class Player {

    private final int id;
    private final String name;
    private final Team team;


    public Player(int id, String name, Team team) {
        this.id = id;
        this.name = name;
        this.team = team;
    }

    public boolean isTeam(Piece piece) {
        return team == piece.getTeam();
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public int getId() {
        return id;
    }
}
