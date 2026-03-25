package janggi.domain.piece;

import janggi.domain.team.TeamType;

public class Soldier implements Piece{

    private final TeamType teamType;

    public Soldier(final TeamType teamType) {
        this.teamType = teamType;
    }

}
