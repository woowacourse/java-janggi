package game;

import piece.Team;

public class Player {

    private final Team team;

    public Player(final Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return team;
    }

}
