package domain;

import domain.piece.Piece;

public class Player {

    private final String name;
    private final Team team;


    public Player(String name, Team team) {
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
}
