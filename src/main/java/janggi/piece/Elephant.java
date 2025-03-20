package janggi.piece;

import janggi.team.Team;
import janggi.position.Position;

public class Elephant implements Piece {

    private final String name = "E"; //sang
    private final Team team;
    private Position position;

    public Elephant(Team team, Position position) {
        this.team = team;
        this.position = position;
    }

    @Override
    public boolean isOccupiedByMe(Position position) {
        return position.equals(this.position);
    }

    @Override
    public void move(Position position) {
        this.position = this.position.update(position);
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
