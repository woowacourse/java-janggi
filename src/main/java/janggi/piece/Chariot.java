package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

public class Chariot implements Piece {

    private final Team team;
    private final Position position;

    public Chariot(Team team, Position position) {
        this.team = team;
        this.position = position;
    }
}
