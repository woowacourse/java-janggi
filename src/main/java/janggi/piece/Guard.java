package janggi.piece;

import janggi.team.Team;
import janggi.position.Position;

public class Guard implements Piece {

    private final String name = "G"; //sa
    private final Team team;
    private final Position position;

    public Guard(Team team, Position position) {
        this.team = team;
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Team getTeam() {
        return team;
    }
}
