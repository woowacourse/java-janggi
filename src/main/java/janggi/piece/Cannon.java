package janggi.piece;

import janggi.team.Team;
import janggi.position.Position;

public class Cannon implements Piece{

    private final String name = "P"; //po
    private final Team team;
    private final Position position;

    public Cannon(Team team, Position position) {
        this.team = team;
        this.position = position;
    }

    @Override
    public boolean isOccupiedByMe(Position position) {
        return position.equals(this.position);
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
