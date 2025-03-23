package janggi.piece;

import janggi.position.Position;
import janggi.team.Team;

public class Soldier implements Piece {

    private final Team team;
    private final Position position;

    public Soldier(Team team, Position position) {
        this.team = team;
        this.position = position;
    }
}
