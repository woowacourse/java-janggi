package janggi.domain.piece;

import janggi.domain.Space;
import janggi.domain.Team;

public abstract class Piece implements Space {

    private final Team team;

    public Piece(Team team) {
        this.team = team;
    }

    @Override
    public boolean isBlank() {
        return false;
    }
}
