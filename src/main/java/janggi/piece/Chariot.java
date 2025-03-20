package janggi.piece;

import janggi.team.Team;
import janggi.position.Position;

public class Chariot implements Piece {

    private final String name = "C"; //cha
    private final Team team;
    private final Position position;

    public Chariot(Team team, Position position) {
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
