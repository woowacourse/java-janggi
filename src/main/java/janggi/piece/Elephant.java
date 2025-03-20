package janggi.piece;

import janggi.team.Team;
import janggi.position.Position;

public class Elephant implements Piece {

    private final String name = "E"; //sang
    private final Team team;
    private final Position position;

    public Elephant(Team team, Position position) {
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
