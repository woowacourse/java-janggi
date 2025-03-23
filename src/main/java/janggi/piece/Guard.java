package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

public class Guard implements Piece {

    private final Team team;
    private final Position position;

    public Guard(Team team, Position position) {
        this.team = team;
        this.position = position;
    }
}
