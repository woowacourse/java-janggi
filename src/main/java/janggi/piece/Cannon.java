package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

public class Cannon implements Piece {

    private final Team team;
    private final Position position;

    public Cannon(Team team, Position position) {
        this.team = team;
        this.position = position;
    }
}
